package com.cnc.outfit_ecommerce.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

@Slf4j
public class JwtUtils {
  private static final long DEFAULT_EXPIRATION_TIME = 31536000000L; // a year

  public static KeyPair generateRSA2048KeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator generator;
    generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    return generator.generateKeyPair();
  }

  public static PublicKey getPublicKeyFromString(String publicKey, String algorithm)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] publicBytes = Base64.decodeBase64(publicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    return keyFactory.generatePublic(keySpec);
  }

  public static PrivateKey getPrivateKeyFromString(String privateKey, String algorithm)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] privateBytes = Base64.decodeBase64(privateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    return keyFactory.generatePrivate(keySpec);
  }

  public static String generateJWT(String privateKey, Map<String, Object> claims)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    return Jwts.builder()
        .signWith(getPrivateKeyFromString(privateKey, "RSA"))
        .addClaims(claims)
        .compact();
  }

  public static String generateJWT(PrivateKey privateKey, Map<String, Object> claims) {
    return Jwts.builder().signWith(privateKey).addClaims(claims).compact();
  }

  public static Claims parseRS256JWT(String jwt, String publicKey)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    return Jwts.parserBuilder()
        .setSigningKey(getPublicKeyFromString(publicKey, "RSA"))
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  public static Claims parseJWTClaims(String jwt) {
    String jwtWithoutKey = jwt.substring(0, jwt.lastIndexOf(".") + 1);
    return (Claims) Jwts.parserBuilder().build().parse(jwtWithoutKey).getBody();
  }
}
