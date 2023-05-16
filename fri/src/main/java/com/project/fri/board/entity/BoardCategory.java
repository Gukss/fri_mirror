package com.project.fri.board.entity;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="board_category")
public class BoardCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="board_category_id")
  private Long id;
  @Enumerated(EnumType.STRING)
  @NotNull
  private Category category;
}
