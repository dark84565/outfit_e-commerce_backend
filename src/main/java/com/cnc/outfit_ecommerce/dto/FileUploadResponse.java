package com.cnc.outfit_ecommerce.dto;

import lombok.Data;

@Data
public class FileUploadResponse {
  private String fileName;
  private String downloadUtil;
  private long size;
}
