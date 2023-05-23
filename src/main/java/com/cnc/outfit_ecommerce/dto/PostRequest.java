package com.cnc.outfit_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

  private String description;

  private List<String> tags;

  // TODO: not null is not work
  @NotBlank
  @JsonProperty("user_uuid")
  private String userUuid;
}
