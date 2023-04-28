package com.project.fri.room.dto;

import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.fri.room.repository fileName       : FindAllRoomByCategoryResponse
 * author         : SSAFY date           : 2023-04-19 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-04-19        SSAFY       최초
 * 생성
 */
@Builder
@AllArgsConstructor
@Data
public class FindAllRoomByCategoryResponse {

  private Long roomId;
  private String title;
  private int headCount;
  private long major;
  private long nonMajor;
  private String location;

  public static FindAllRoomByCategoryResponse create(Room room, long major, long nonMajor) {
    FindAllRoomByCategoryResponse findAllRoom = FindAllRoomByCategoryResponse.builder()
        .roomId(room.getId())
        .title(room.getTitle())
        .headCount(room.getHeadCount())
        .major(major)
        .nonMajor(nonMajor)
        .location(room.getLocation())
        .build();
    return findAllRoom;
  }

}
