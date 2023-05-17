package com.project.fri.board.entity;

import com.project.fri.user.entity.User;
import com.project.fri.util.BaseEntity;
import com.project.fri.util.BaseTimeEntity;

import javax.persistence.*;
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
@Table(name="board_image")
public class BoardImage extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="board_image_id")
  private Long id;
  @NotNull
  private String boardUrl;

  private boolean isDelete;
  @Embedded
  @NotNull
  private BaseEntity baseEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  @NotNull
  private Board board;

  //==셍성 메서드==//
  public static BoardImage create(String imageUrl, User user,Board board) {
    return BoardImage.builder()
            .boardUrl(imageUrl)
            .baseEntity(BaseEntity.builder()
                    .updater(user.getNickname())
                    .constructor(user.getNickname())
                    .build())
            .board(board)
            .isDelete(false)
            .build();

  }
}
