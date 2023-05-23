package com.cnc.outfit_ecommerce.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  List<String> savePostImage(MultipartFile[] multipartFiles) throws IOException;
}
