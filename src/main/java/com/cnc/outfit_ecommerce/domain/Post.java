package com.cnc.outfit_ecommerce.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Post {

  private Long id;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  private String description;

  private String userUuid;

  private List<String> photoPaths;

  private List<String> tags;
}
