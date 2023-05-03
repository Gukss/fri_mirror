package com.project.fri.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="anonymous_profile_image")
public class AnonymousProfileImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="anonymous_profile_image_id")
  private Long id;

  @NotNull
  private String imageUrl;
}
