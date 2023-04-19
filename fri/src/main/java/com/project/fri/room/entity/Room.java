package com.project.fri.room.entity;

import com.project.fri.common.entity.Area;
import com.project.fri.util.BaseEntity;
import com.project.fri.util.BaseTimeEntity;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.room.entity fileName       : Room date           : 2023-04-18
 * description    :
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="room")
public class Room extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="room_id")
  private Long id;

  private String title;

  private int headCount;

  private boolean isDelete;

  private boolean isConfirmed;

  private String location;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_category_id")
  private RoomCategory roomCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_id")
  private Area area;

  @Embedded
  @NotNull
  private BaseEntity baseEntity;
}
