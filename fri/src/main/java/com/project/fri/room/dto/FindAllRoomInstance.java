package com.project.fri.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.room.dto fileName       : FindAllRoomInstance date           :
 * 2023-04-19 description    :
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllRoomInstance {
  private Long roomId;
  private String title;
  private String location;
  private String roomCategory;
  private int headCount;
  private int major;
  private int nonMajor;
}
