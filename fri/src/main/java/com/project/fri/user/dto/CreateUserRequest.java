package com.project.fri.user.dto;

import com.project.fri.common.entity.Category;
import lombok.Data;

/**
 * packageName    : com.project.fri.user.dto fileName       : createMemberRequest author         :
 * hagnoykmik date           : 2023-04-24 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-24        hagnoykmik       최초 생성
 */

@Data
public class CreateUserRequest {

  private String name;

  private String email;

  private String password;

  private String nickname;

  private Category area;

  private String year;

  private boolean major;

}
