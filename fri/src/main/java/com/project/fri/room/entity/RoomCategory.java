package com.project.fri.room.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.room.entity fileName       : RoomCategory date           :
 * 2023-04-18 description    :
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="room_category")
public class RoomCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="room_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private Category category;
}
