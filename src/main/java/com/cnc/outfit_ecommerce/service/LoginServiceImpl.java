package com.cnc.outfit_ecommerce.service;

import static java.lang.String.format;

import com.cnc.outfit_ecommerce.auth.JwtUtils;
import com.cnc.outfit_ecommerce.dao.KeyPairModelRepository;
import com.cnc.outfit_ecommerce.dao.UserModelRepository;
import com.cnc.outfit_ecommerce.domain.User;
import com.cnc.outfit_ecommerce.entity.KeyPairModel;
import com.cnc.outfit_ecommerce.exception.OutfitECommerceException;
import com.cnc.outfit_ecommerce.mapper.UserModelMapper;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
  private final UserModelRepository userModelRepository;
  private final UserModelMapper userModelMapper;
  private final KeyPairModelRepository keyPairModelRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String createUser(User user) {
    encodePassword(user);

    String userUuid = UUID.randomUUID().toString();

    user.setUuid(userUuid);
    userModelRepository.save(userModelMapper.toModel(user));

    return generateApiPermissionJWT(userUuid);
  }

  @Override
  public String verifyUserIdentity(User user) {
    // TODO: check api permission jwt exists or not, situation that remove jwt after create user
    User userInDB =
        userModelMapper.fromModel(
            userModelRepository
                .findByEmail(user.getEmail())
                .orElseThrow(
                    () ->
                        new OutfitECommerceException(
                            format("email [%s] does not exist", user.getEmail()))));
    if (!passwordEncoder.encode(user.getPassword()).equals(userInDB.getPassword())) {
      throw new OutfitECommerceException("password is not correct");
    }

    // TODO: check jwt expired or not
    return generateLoginPermissionJWT();
  }

  private void encodePassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  private String generateApiPermissionJWT(String userUuid) {
    try {
      KeyPair keyPair = JwtUtils.generateRSA2048KeyPair();

      storeJwtKeyPair(keyPair, userUuid);

      Map<String, Object> claims = Map.of("user_uuid", userUuid);

      return JwtUtils.generateJWT(keyPair.getPrivate(), claims);
    } catch (Exception e) {
      throw new OutfitECommerceException("generate user jwt failed", e);
    }
  }

  // TODO: implement
  private String generateLoginPermissionJWT() {
    return "";
  }

  // TODO: handle expired jwt
  private void storeJwtKeyPair(KeyPair keyPair, String userUuid) {
    try {
      keyPairModelRepository.save(
          KeyPairModel.builder()
              .privateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()))
              .publicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()))
              .userUuid(userUuid)
              .build());
    } catch (Exception e) {
      throw new OutfitECommerceException("store jwt key pair failed", e);
    }
  }
}
