package com.project.fri.user.controller;

import com.project.fri.user.dto.*;
import com.project.fri.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * packageName    : com.project.fri.user.controller fileName       : UserController date           :
 * 2023-04-18 description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j

public class UserController {

  private final UserService userService;

  @PatchMapping("/room/{roomId}")
  public ResponseEntity<UpdateUserRoomResponse> updateUserRoom(@PathVariable("roomId") Long roomId,
      @RequestBody UpdateUserRoomRequest request, @RequestHeader("Authorization") Long userId) {
    ResponseEntity<UpdateUserRoomResponse> res = userService.updateUserRoom(roomId, request,
        userId);
    return res;
  }

  @PostMapping
  public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
    // todo: 프로필 파일 + @requestPart
    HttpStatus status = userService.createUser(createUserRequest);
    return ResponseEntity.status(status).build();
  }

  @PostMapping("/certified/edu")
  public ResponseEntity<CertifiedEduResponse> certifiedEdu(
      @RequestBody CertifiedEduRequest certifiedEduRequest) {
    CertifiedEduResponse certifiedEduResponse = userService.certifiedEdu(certifiedEduRequest);
    ResponseEntity<CertifiedEduResponse> res = null;
    if (certifiedEduResponse.isCertifiedEdu()) {
      res = ResponseEntity.ok().body(certifiedEduResponse);
    } else {
      res = ResponseEntity.badRequest().body(certifiedEduResponse);
    }
    return res;
  }

  @PostMapping("/certified/code")
  public ResponseEntity<CertifiedCodeResponse> certifiedCode(
      @RequestBody CertifiedCodeRequest certifiedCodeRequest) {
    CertifiedCodeResponse certifiedCodeResponse = userService.certifiedCode(certifiedCodeRequest);
    ResponseEntity<CertifiedCodeResponse> res = null;
    if (certifiedCodeResponse.isCertifiedCode()) {
      res = ResponseEntity.ok().body(certifiedCodeResponse);
    } else {
      res = ResponseEntity.badRequest().body(certifiedCodeResponse);
    }
    return res;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Object> signInUser(@Valid @RequestBody SignInUserRequest signInUserRequest,
      HttpServletResponse response) {
    SignInUserResponse result = userService.signIn(signInUserRequest);

    if (result == null) {
      return ResponseEntity.badRequest().body(new SignInErrorResponse("아이디 또는 비밀번호가 맞지 않습니다."));
    }

    Cookie cookie = new Cookie("Authorization", result.getUserId().toString());
    response.addCookie(cookie);
    return ResponseEntity.ok().body(result);
  }

    @PatchMapping
    public ResponseEntity<Object> updateUserProfile(
            @RequestBody UpdateUserProfileRequest updateUserProfileRequest,
            @RequestHeader("Authorization") Long userId) {
        UpdateUserProfileResponse result = userService.updateUserProfile(updateUserProfileRequest, userId);

        // 중복닉네임일때 null 반환
        if (result == null) {
            return ResponseEntity.badRequest().body(new SignInErrorResponse("이미 사용중인 닉네임입니다."));
        }
        return ResponseEntity.ok().body(result);
    }

  @GetMapping
  public ResponseEntity<FindUserResponse> findUser(@RequestHeader("Authorization") Long userId) {
    FindUserResponse result = userService.findUser(userId);

    return ResponseEntity.ok().body(result);
  }

  @PostMapping("/certified/nickname")
  public ResponseEntity certifiedNickname(@RequestBody CertifiedNicknameRequest certifiedNicknameRequest){
    HttpStatus httpStatus = userService.certifiedNickname(certifiedNicknameRequest);
    return ResponseEntity.status(httpStatus).build();
  }
}
