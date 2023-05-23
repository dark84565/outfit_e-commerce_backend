package com.cnc.outfit_ecommerce.controller;

import static java.lang.String.format;

import com.cnc.outfit_ecommerce.dto.FileUploadResponse;
import com.cnc.outfit_ecommerce.dto.PostRequest;
import com.cnc.outfit_ecommerce.dto.PostResponse;
import com.cnc.outfit_ecommerce.exception.OutfitECommerceException;
import com.cnc.outfit_ecommerce.mapper.PostDtoMapper;
import com.cnc.outfit_ecommerce.service.FileStorageService;
import com.cnc.outfit_ecommerce.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
  private final FileStorageService fileStorageService;
  private final UserService userService;
  private final PostDtoMapper postDtoMapper;

  @PostMapping("/upload")
  public ResponseEntity<FileUploadResponse> uploadFile(
      @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {

    fileStorageService.savePostImage(multipartFiles);

    FileUploadResponse response = new FileUploadResponse();
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/api/user/post")
  public ResponseEntity<FileUploadResponse> createPost(
      @RequestPart("files") MultipartFile[] multipartFiles,
      @RequestPart("json") @Valid PostRequest postRequest)
      throws IOException {
    List<String> filePaths = fileStorageService.savePostImage(multipartFiles);

    userService.savePost(postDtoMapper.fromRequest(postRequest, filePaths));

    FileUploadResponse response = new FileUploadResponse();
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/api/user/post/{userUuid}")
  public ResponseEntity<List<PostResponse>> getPost(@PathVariable String userUuid) {
    List<PostResponse> postResponses = postDtoMapper.toResponse(userService.getPost(userUuid));
    return ResponseEntity.ok(postResponses);
  }

  @GetMapping(path = "/user_post_image/{imagePath}")
  public ResponseEntity<Resource> getImage(@PathVariable String imagePath) {
    Resource resource = new FileSystemResource("user_post_image/" + imagePath);
    String fileExtension = FilenameUtils.getExtension(resource.getFilename());
    MediaType mediaType;
    switch (Optional.ofNullable(fileExtension)
        .orElseThrow(
            () ->
                new OutfitECommerceException(
                    format("User post image: [%s] has no file extension", imagePath)))) {
      case "png", "PNG" -> mediaType = MediaType.IMAGE_PNG;
      case "jpg", "JPG", "jpeg", "JPEG" -> mediaType = MediaType.IMAGE_JPEG;
      default -> throw new OutfitECommerceException("Unknown image type.");
    }
    return ResponseEntity.ok().contentType(mediaType).body(resource);
  }
}
