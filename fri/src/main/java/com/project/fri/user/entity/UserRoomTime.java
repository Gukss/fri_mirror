package com.project.fri.user.entity;

import com.project.fri.room.entity.Room;
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

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="user_room_time")
public class UserRoomTime extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_room_time_id")
  private Long id;
  private boolean isDelete;
  @Embedded
  @NotNull
  private BaseEntity baseEntity;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private Room room;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public static UserRoomTime create(User user,Room room){
    UserRoomTime time=UserRoomTime.builder()
        .user(user)
        .room(room)
        .isDelete(false)
        .baseEntity(BaseEntity.builder()
            .updater(user.getNickname())
            .constructor(user.getNickname())
            .build())
        .build();
    return time;
  }

  public UserRoomTime updateIsDelete(boolean isDelete){
    this.isDelete=isDelete;
    return this;
  }
}
