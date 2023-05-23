package com.cnc.outfit_ecommerce.entity;

import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "post")
public class PostModel extends BaseModel {

  private String description;

  @Column(name = "user_uuid")
  private String userUuid;

  @ManyToOne
  @JoinColumn(
      name = "user_uuid",
      referencedColumnName = "uuid",
      insertable = false,
      updatable = false)
  private UserModel userModel;

  // TODO: use list
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "post_id", nullable = false)
  private Set<PhotoModel> photos;

  // TODO: use list
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "post_tag",
      joinColumns = @JoinColumn(name = "post_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<TagModel> tags;
}
