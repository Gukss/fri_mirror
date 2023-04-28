package com.project.fri.user.entity;

import com.project.fri.common.entity.Area;
import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.room.entity.Room;
import com.project.fri.user.dto.CreateUserRequest;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.entity fileName       : User date           : 2023-04-18
 * description    :
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="user")
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_id")
  private Long id;

  private boolean isMajor;

  private String name;

  private String year;

  private String email;

  private String profileUrl;

  private String password;

  private int heart;

  private String nickname;

  private boolean isCertified;

  private boolean isDelete;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_id")
  private Area area;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_room_id")
  private GameRoom gameRoom;

  @Embedded
  @NotNull
  private BaseEntity baseEntity;

  //==비즈니스 로직==//
  /**
   * 방 생성 시 방 update(번호는 자동으로 pk값으로 update됨)
   */
  public Room updateRoomNumber(Room saveRoom) {
    this.room = saveRoom;
    return room;
  }

  /**
   * desc: 사용자가 ready를 눌렀을 때 입력받은 값으로 ready를 update한다.
   * @param //ready
   * @return
   */
//  public User updateReady(boolean ready){
//    this.ready = ready;
//    return this;
//  }

  //==생성메서드==//
  public static User create(CreateUserRequest request, Area area) {
    User user = User.builder()
        .name(request.getName())
        .area(area)
        .isMajor(request.isMajor())
        .year(request.getYear())
        .email(request.getEmail())
//        .profileUrl()
        .password(request.getPassword())
        .heart(5)
        .nickname(request.getNickname())
        .isCertified(false)  // 인증 전
        .baseEntity(BaseEntity.builder()
            .constructor(request.getName())
            .updater(request.getName())
            .build())
        .build();
    return user;
  }
}
