package com.project.fri.chatting.entity;

import com.project.fri.chatting.dto.CreateChattingMessageRequest;
import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
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
@Table(name="chatting")
public class ChattingMessage extends BaseTimeEntity {
  // null 값을 허용하지 않기 위해 원시타입 사용 하지만 String은 참조타입이여서 별도로 @NotNull추가
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="chatting_id")
  private Long id;
  @NotNull
  private String message;
  private boolean isDelete;
  @Embedded
  @NotNull
  private BaseEntity baseEntity;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="room_id")
  private Room room;

  public static ChattingMessage create(CreateChattingMessageRequest request,User user,Room room){
    ChattingMessage response=ChattingMessage.builder()
        .message(request.getMessage())
        .user(user)
        .room(room)
        .isDelete(false)
        .baseEntity(BaseEntity.builder()
            .constructor(user.getName())
            .updater(user.getName())
            .build())
        .build();

    return response;
  }
}
