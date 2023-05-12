package com.project.fri.comment.entity;

import com.project.fri.board.entity.Board;
import com.project.fri.comment.dto.CreateCommentRequest;
import com.project.fri.user.entity.User;
import com.project.fri.util.BaseEntity;
import com.project.fri.util.BaseTimeEntity;
import java.time.LocalDateTime;
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
@Table(name="comment")
public class Comment extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="comment_id")
  private long id;

  @NotNull
  @Column(length = 511)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  @NotNull
  private Board board;
  private boolean isDelete;
  @Embedded
  @NotNull
  private BaseEntity baseEntity;


  public static Comment create(CreateCommentRequest createCommentRequest,User user,Board board){
    Comment comment = Comment.builder()
        .board(board)
        .user(user)
        .content(createCommentRequest.getContent())
        .baseEntity(BaseEntity.builder()
            .constructor(user.getNickname())
            .updater(user.getNickname())
            .build())
        .build();

    return comment;
  }

  public Comment updateIsDelete(boolean isDelete) {
    this.isDelete = isDelete;
    return this;
  }

  public Comment updateComment(String content){
    this.content=content;
    this.update(LocalDateTime.now());
    return this;
  }

}
