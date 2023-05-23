package com.cnc.outfit_ecommerce.context;

public class JwtContext {

  private static final ThreadLocal<String> userUuid = new ThreadLocal<>();

  public static String getUserUuid() {
    return userUuid.get();
  }

  public static void setUserUuid(String userUuid) {
    JwtContext.userUuid.set(userUuid);
  }
}
