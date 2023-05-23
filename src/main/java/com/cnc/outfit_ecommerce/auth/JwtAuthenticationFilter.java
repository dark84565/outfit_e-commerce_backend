package com.cnc.outfit_ecommerce.auth;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.cnc.outfit_ecommerce.context.JwtContext;
import com.cnc.outfit_ecommerce.dao.KeyPairModelRepository;
import com.cnc.outfit_ecommerce.entity.KeyPairModel;
import com.cnc.outfit_ecommerce.exception.OutfitECommerceException;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final KeyPairModelRepository keyPairModelRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException {
    try {
      String jwt = extractToken(request);
      String publicKey = getPublicKey(jwt);

      Claims claims = JwtUtils.parseRS256JWT(jwt, publicKey);
      JwtContext.setUserUuid((String) claims.get("user_uuid"));

      filterChain.doFilter(request, response);
    } catch (InvalidKeySpecException e) {
      log.error(e.getMessage(), e);
      response.setStatus(SC_FORBIDDEN);
      response.getOutputStream().print(e.getMessage());
    } catch (OutfitECommerceException e) {
      log.error(e.getMessage(), e);
      response.setStatus(SC_UNAUTHORIZED);
      response.getOutputStream().print(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      response.setStatus(SC_UNAUTHORIZED);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return requestURI.equals("/api/login/register")
        || requestURI.equals("/api/login/login")
        || requestURI.startsWith("/user_post_image");
  }

  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (isNull(bearerToken)) {
      throw new OutfitECommerceException("Cannot find JWT in header [Authorization]");
    }
    if (bearerToken.startsWith("Bearer ")) {
      bearerToken = bearerToken.replace("Bearer ", "");
    }
    return bearerToken;
  }

  private String getPublicKey(String jwt) {
    Claims claims = JwtUtils.parseJWTClaims(jwt);
    String userUuid = (String) claims.get("user_uuid");
    KeyPairModel keyPairModel =
        keyPairModelRepository
            .findByUserUuid(userUuid)
            .orElseThrow(
                () ->
                    new OutfitECommerceException(format("Cannot find user uuid: [%s]", userUuid)));
    return keyPairModel.getPublicKey();
  }
}
