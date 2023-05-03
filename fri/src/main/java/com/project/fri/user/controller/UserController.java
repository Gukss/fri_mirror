package com.project.fri.user.controller;

import com.project.fri.user.dto.*;
import com.project.fri.user.service.UserService;
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
    public ResponseEntity<UpdateUserRoomResponse> updateUserRoom(@PathVariable("roomId") Long roomId, @RequestBody UpdateUserRoomRequest request) {
        Long userId = 4L;
      ResponseEntity<UpdateUserRoomResponse> res = userService.updateUserRoom(
          roomId, request, userId);
      return res;
    }

  @PatchMapping("{roomId}/ready")
  public ResponseEntity<UpdateUserReadyResponse> updateUserReady(@PathVariable Long roomId) {
    //todo: userId는 토큰에서 가지고 와서 넣어주기(updateUserReady에서)
    UpdateUserReadyResponse updateUserReadyResponse = userService.updateUserReady(1L, roomId);
    URI uri = URI.create(roomId+"/ready");
    return ResponseEntity.created(uri).body(updateUserReadyResponse);
  }

  @PostMapping
  public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
    // todo: 프로필 파일 + @requestPart
    userService.createUser(createUserRequest);
    return ResponseEntity.status(201).build();
  }

  @PostMapping("/certified/edu")
  public ResponseEntity<CertifiedEduResponse> certifiedUser(@RequestBody CertifiedEduRequest certifiedEduRequest){
      CertifiedEduResponse certifiedEduResponse = userService.certifiedEdu(certifiedEduRequest);
      ResponseEntity<CertifiedEduResponse> res = null;
      if(certifiedEduResponse.isCertifiedEdu()){
        res = ResponseEntity.ok().body(certifiedEduResponse);
      }else{
        res = ResponseEntity.badRequest().body(certifiedEduResponse);
      }
      return res;
  }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signInUser(@Valid @RequestBody SignInUserRequest signInUserRequest, HttpServletResponse response, HttpServletRequest request) {
        SignInUserResponse result = userService.signIn(signInUserRequest);

        if (result == null) {
            return ResponseEntity.badRequest().body(new SignInErrorResponse("아이디 또는 비밀번호가 맞지 않습니다."));
        }

        HttpSession session = request.getSession();
        session.setAttribute("userId", result.getUserId());

        Cookie cookie = new Cookie("sessionId", session.getId());
        response.addCookie(cookie);
        return ResponseEntity.ok().body(result);
    }
}
