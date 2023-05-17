package com.project.fri.user.service;

import com.project.fri.user.dto.*;
import com.project.fri.user.entity.Certification;
import com.project.fri.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * packageName    : com.project.fri.user.service fileName       : UserService date           :
 * 2023-04-18 description    :
 */
public interface UserService {

  /**
   * desc: id로 user 찾아오기
   * @param userId
   * @return
   */
  User findById(long userId);

  /**
   * 방 입장시 User의 room 업데이트
   * @param roomId
   * @param request
   * @return
   */
  ResponseEntity<UpdateUserRoomResponse> updateUserRoom(Long roomId, UpdateUserRoomRequest request, Long userId);

  /**
   * desc: 회원가입
   * @return
   */
  HttpStatus createUser(CreateUserRequest request);

  /**
   * desc: 에듀싸피에 등록된 이메일인지 검증
   * @param certifiedEduRequest
   * @return
   */
  CertifiedEduResponse certifiedEdu(CertifiedEduRequest certifiedEduRequest);

  /**
   * desc: 이메일로 보낸 코드와 일치하는지 검증
   * @param certifiedCodeRequest
   * @return
   */
  CertifiedCodeResponse certifiedCode(CertifiedCodeRequest certifiedCodeRequest);

  /**
   * desc: 로그인요청시 email, password 확인 맞으면 해당 User 객체, 틀리면 null 반환
   * @param signInUserRequest
   * @return
   */
  SignInUserResponse signIn(SignInUserRequest signInUserRequest);

  /**
   * 유저 정보 조회
   * @param userId
   * @return
   */
  FindUserResponse findUser(Long userId);

  /**
   * 유저 프로필수정 (닉네임, 사진)
   * @param updateUserProfileRequest 프로필 수정 요청
   * @param userId 변경 유저
   * @return 프로필 수정 응답
   */
  UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest, Long userId);

  /**
   * 닉네임 중복 체크
   * @param certifiedNicknameRequest
   * @return
   */
  HttpStatus certifiedNickname(CertifiedNicknameRequest certifiedNicknameRequest);

  /**
   * 매일 00시 유저 하트수 1개씩 증가
   */
  void updateUserHeartScheduler();

  /**
   * 유저 프로필 수정을 위한 사진 리스트 조회
   * @return FindAnonymousProfileImagesResponse
   */
  FindAnonymousProfileImagesResponse findAnonymousProfileImages();

  UpdateIsReadyResponse updateIsReady(Long userId, UpdateIsReadyRequest updateIsReadyRequest);
  ResponseEntity updateEmailAgreement(Long userId, UpdateEmailAgreementRequest updateEmailAgreementRequest);
}
