package com.cnc.outfit_ecommerce.service;

import com.cnc.outfit_ecommerce.domain.User;

public interface LoginService {

  String createUser(User user);

  String verifyUserIdentity(User user);
}
