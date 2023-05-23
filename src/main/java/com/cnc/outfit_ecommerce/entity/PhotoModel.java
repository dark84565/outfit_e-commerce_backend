package com.cnc.outfit_ecommerce.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "photo")
public class PhotoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String path;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false, insertable = false, updatable = false)
  private PostModel post;
}
