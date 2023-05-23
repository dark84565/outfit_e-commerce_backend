package com.cnc.outfit_ecommerce.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

  private static final String USER_POST_PATH = "user_post_image";
  private final Path postUploadedPath = Paths.get(USER_POST_PATH);

  @PostConstruct
  private void init() throws IOException {
    if (!Files.exists(postUploadedPath)) {
      Files.createDirectories(postUploadedPath);
    }
  }

  @Override
  public List<String> savePostImage(MultipartFile[] multipartFiles) throws IOException {
    if (multipartFiles == null) {
      throw new RuntimeException("User post image is null");
    }

    List<String> photoPaths = new ArrayList<>();

    for (MultipartFile multipartFile : multipartFiles) {
      String contentType = multipartFile.getContentType();
      if (contentType == null
          || !(contentType.equals("image/png")
              || contentType.equals("image/jpg")
              || contentType.equals("image/jpeg"))) {
        throw new RuntimeException("User post image file is not an image");
      }

      // TODO: need to limit the media size
      long size = multipartFile.getSize();

      String fileName = multipartFile.getOriginalFilename();
      String fileExtension = FilenameUtils.getExtension(fileName);
      String fileCode = UUID.randomUUID().toString();

      try (InputStream inputStream = multipartFile.getInputStream()) {
        Path filePath = postUploadedPath.resolve(fileCode + "." + fileExtension);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        throw new IOException("Could not save file: " + fileName, e);
      }

      photoPaths.add(String.format("/%s/%s.%s", USER_POST_PATH, fileCode, fileExtension));
    }

    return photoPaths;
  }
}
