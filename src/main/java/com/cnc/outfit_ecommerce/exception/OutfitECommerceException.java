package com.cnc.outfit_ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutfitECommerceException extends RuntimeException {
  String message;

  public OutfitECommerceException(String message) {
    this.message = message;
  }

  public OutfitECommerceException(String message, Throwable throwable) {
    super(throwable);
    this.message = message;
  }
}
