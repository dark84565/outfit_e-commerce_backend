package com.cnc.outfit_ecommerce.controller;

import com.cnc.outfit_ecommerce.dto.UserRequest;
import com.cnc.outfit_ecommerce.mapper.UserDtoMapper;
import com.cnc.outfit_ecommerce.service.LoginService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
  private final LoginService loginService;
  private final UserDtoMapper userDtoMapper;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest) {
    String apiPermissionJWT = loginService.createUser(userDtoMapper.fromRequest(userRequest));
    return ResponseEntity.ok(apiPermissionJWT);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid UserRequest userRequest) {
    String loginPermissionJWT =
        loginService.verifyUserIdentity(userDtoMapper.fromRequest(userRequest));
    return ResponseEntity.ok(loginPermissionJWT);
  }
}
