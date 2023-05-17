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

  @NotNull
  private String name;
  @NotNull
  private String year;
  @NotNull
  private String email;
  @NotNull
  private String profileUrl;
  @NotNull
  private String password;

  private int heart;
  @NotNull
  private String nickname;

  private boolean isCertified;

  private boolean isDelete;
  @NotNull
  private String salt;
  private boolean isReady;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_id")
  @NotNull
  private Area area;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_room_id")
  private GameRoom gameRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "anonymous_profile_image_id")
  private AnonymousProfileImage anonymousProfileImage;

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

  public User minusHeart(){
    this.heart -= 1;
    return this;
  }

  /**
   * 하트수 증가
   */
  public void plusHeart() {
    this.heart += 1;
  }

  /**
   * 게임 방 참여하기 눌렀을 때, 생성했을 때 게임 방 update
   */
  public GameRoom updateGameRoomNumber(GameRoom saveGameRoom) {
    this.gameRoom = saveGameRoom;
    return gameRoom;
  }

  public User updateReady(boolean isReady){
    this.isReady = isReady;
    return this;
  }

  /**
   * desc: 사용자가 ready를 눌렀을 때 입력받은 값으로 ready를 update한다.
   *
   * @param //ready
   * @return
   */
//  public User updateReady(boolean ready){
//    this.ready = ready;
//    return this;
//  }

  /**
   * desc: 프로필 수정
   * @param anonymousProfileImage
   * @param nickname
   * @return
   */
  public User updateUserProfile(AnonymousProfileImage anonymousProfileImage, String nickname) {
    this.anonymousProfileImage = anonymousProfileImage;
    this.nickname = nickname;

    return this;
  }

  //==생성메서드==//
  public static User create(CreateUserRequest request, Area area,String salt,String encrypt) {
    return User.builder()
        .name(request.getName())
        .area(area)
        .isMajor(request.isMajor())
        .year(request.getYear())
        .email(request.getEmail())
        .profileUrl("test") // todo:실제 url로 변경
        .password(encrypt)
        .salt(salt)
        .heart(5)
        .isReady(false)
        .anonymousProfileImage(AnonymousProfileImage.builder().id(1L).build()) // todo : 나중에 랜덤 값으로 변경
        .nickname(request.getNickname())
        .isCertified(true)  // 인증이 완료되어야만 생성할 수 있기 때문에 true 하지만 추후 확장성을 위해 필드는 놔둠
        .baseEntity(BaseEntity.builder()
            .constructor(request.getName())
            .updater(request.getName())
            .build())
        .build();
  }
}
