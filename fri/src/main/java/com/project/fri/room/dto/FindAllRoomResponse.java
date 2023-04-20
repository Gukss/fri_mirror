package com.project.fri.room.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.room.dto fileName       : FindAllRoomResponse date           :
 * 2023-04-19 description    :
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllRoomResponse {
  List<FindAllRoomInstance> drink;
  List<FindAllRoomInstance> game;
  List<FindAllRoomInstance> meal;
  List<FindAllRoomInstance> exercise;
  List<FindAllRoomInstance> stydy;
  List<FindAllRoomInstance> betting;
  List<FindAllRoomInstance> etc;
}
