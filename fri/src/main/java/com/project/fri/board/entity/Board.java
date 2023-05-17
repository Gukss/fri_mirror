package com.project.fri.board.entity;

import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.user.entity.User;
import com.project.fri.util.BaseEntity;
import com.project.fri.util.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "board")
public class Board extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Long id;
  @NotNull
  private String title;
  @NotNull
  @Column(length = 3000)
  private String content;
  private boolean isDelete;
  private int likesCount;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_category_id")
  @NotNull
  private BoardCategory boardCategory;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;
  @Embedded
  @NotNull
  private BaseEntity baseEntity;

  public static Board create(CreateBoardRequest createBoardRequest, User user,
      BoardCategory boardCategory) {
    return Board.builder()
        .title(createBoardRequest.getTitle())
        .content(createBoardRequest.getContent())
        .likesCount(0)
        .boardCategory(boardCategory)
        .user(user)
        .baseEntity(BaseEntity.builder()
            .constructor(user.getNickname())
            .updater(user.getNickname())
            .build())
        .build();
  }

  public Board updateIsDelete(boolean isDelete){
    this.isDelete = isDelete;
    return this;
  }

  public void updateLikesCount(int cnt) {
    this.likesCount += cnt;
    if (this.likesCount < 0) {
      this.likesCount = 0;
    }
  }
}
