package com.project.fri.user.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.gameRoom.dto.SocketGameRoomStatusRequestAndResponse;
import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.gameRoom.repository.GameRoomRepository;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.dto.*;
import com.project.fri.user.entity.AnonymousProfileImage;
import com.project.fri.user.entity.Certification;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.AnonymousProfileImageRepository;
import com.project.fri.user.repository.CertificationRepository;
import com.project.fri.user.repository.UserRepository;
import java.util.Random;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import com.project.fri.util.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName    : com.project.fri.user.service fileName       : UserServiceImpl date           :
 * 2023-04-19 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final GameRoomRepository gameRoomRepository;

  private final RoomRepository roomRepository;
  private final AreaRepository areaRepository;
  private final UserRepository userRepository;
  private final CertificationRepository certificationRepository;
  private final AnonymousProfileImageRepository anonymousProfileImageRepository;

  private WebDriver driver;
  private final JavaMailSender emailSender;
  private final SimpMessageSendingOperations messagingTemplate;


//  public static final String ePw = createKey();

  private static final String URL = "https://edu.ssafy.com/comm/login/SecurityLoginForm.do";
  private static final String LOCAL_PATH = "D:\\Guk\\fri\\chromedriver.exe";
  private static final String SERVER_PATH = "/usr/bin/chromedriver";
  private static final String containerIp = "172.17.0.5";

  @Override
  public User findById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
  }

  @Override
  @Transactional
  public ResponseEntity<UpdateUserRoomResponse> updateUserRoom(Long roomId,
      UpdateUserRoomRequest request,
      Long userId) {
    Optional<User> user = userRepository.findById(userId);
    User findUser = user.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Optional<Room> room = roomRepository.findById(roomId);
    Room findRoom = room.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    // 삭제된 방인지 먼저 확인
    if (Boolean.TRUE.equals(findRoom.isDelete())) {
      throw new NotFoundExceptionMessage((NotFoundExceptionMessage.NOT_FOUND_ROOM));
    }

    boolean participate = true;

    ResponseEntity res = null;
    UpdateUserRoomResponse updateUserRoomResponse = null;
    log.info("findUser.getRoom(): " + findUser.getRoom());
    if (findUser.getRoom() == null) {
      // 해당 유저가 어떤방에도 입장하지 않은 상태일 때 -> 바로 입장
      if (findUser.getHeart() >= 1) { //하트 1이상일 때
        if (Boolean.FALSE.equals(request.isParticipate())) { //false로 오면 참여하기를 누른거다.
          //하트가 1이상인지 검사하고, 1이상이면 입장 후 하트-=1, 1미만이면 입장하면 안된다.
          findUser.updateRoomNumber(findRoom);
          findUser.minusHeart();
        } else {
          //true로 오면 나가기를 누른거다. => but room이 null인데 true가 올 수 없다.
          participate = false;
        }
      } else { //하트 1미만일 때
        participate = false;
        updateUserRoomResponse = UpdateUserRoomResponse.builder()
            .roomId(findRoom.getId())
            .title(findRoom.getTitle())
            .isParticipate(participate)
            .build();
        return res = ResponseEntity.status(HttpStatus.FORBIDDEN).body(updateUserRoomResponse);
      }
    } else if (findUser.getRoom().equals(findRoom) && Boolean.TRUE.equals(
        request.isParticipate())) {
      // 입장중인 방과 동일하면 퇴장 만약시 남은 인원이 없으면 방 삭제 및 유저 ready상태 false
      findUser.updateRoomNumber(null);
      //ready 상태 false만들기 -> merge 하고난 후
      participate = false;
      // 해당방에 유저가 남아 있는지 확인 없으면 방 삭제
      List<User> findUsers = userRepository.findAllByRoom(findRoom);
      if (findUsers.isEmpty()) {
        findRoom.deleteRoom();
      }

      //todo: 방 나갈 때 heart 줄도록 구현 필요
    } else {
      // 방에 입장 상태이지만 기존 입장중이 방과 입장하려는 방이 일치하지 않을 때
      throw new IllegalStateException("입장중인 방과 일치하지 않습니다.");
    }

    updateUserRoomResponse = UpdateUserRoomResponse.builder()
        .roomId(findRoom.getId())
        .title(findRoom.getTitle())
        .isParticipate(participate)
        .build();

    res = ResponseEntity.ok().body(updateUserRoomResponse);
    return res;
  }

  /**
   * desc: 회원가입
   *
   * @return
   */
  @Override
  public HttpStatus createUser(CreateUserRequest request) {

    // area 찾기
    Area area = areaRepository.findByCategory(request.getArea())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    //certification 이메일로 뒤져서 isConfirmedEdu, isConfirmedCode 둘 다 true일 때 가입시켜주기
    Certification certification = certificationRepository.findByEmail(request.getEmail())
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_CERTIFICATION));
    HttpStatus responseStatus = null;
    Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
    if (optionalUser.isPresent()) { //이메일로 등록된 유저가 있을 때 400바로 반환한다.
      responseStatus = HttpStatus.BAD_REQUEST;
      return responseStatus;
    }

    //todo: 이메일 중복 확인 => 같은 이메일로 user table에 들어있으면 다시 가입안되도록 막아야한다.
    // db에 등록되있는 email이면 가입안되게 => error or status 반환
    if (certification.isConfirmedCode() && certification.isConfirmedEdu()) { //둘 다 true일 때
      String salt = Encrypt.getSalt();
      String encrypt = Encrypt.getEncrypt(request.getPassword(), salt);
      // user db에 저장
      User user = User.create(request, area, salt, encrypt);
      User saveUser = userRepository.save(user);
      responseStatus = HttpStatus.CREATED;
    } else {
      responseStatus = HttpStatus.UNAUTHORIZED;
    }
    return responseStatus;
  }

  /**
   * desc: 셀레니움을 이용한 유저 이메일 검증
   *
   * @param certifiedEduRequest
   */
  @Override
  @Transactional
  public CertifiedEduResponse certifiedEdu(CertifiedEduRequest certifiedEduRequest) {
    boolean isConfirmedEdu = false;
    String email = certifiedEduRequest.getEmail();
    //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)
//    System.setProperty("webdriver.chrome.driver", LOCAL_PATH);
    System.setProperty("webdriver.chrome.driver", SERVER_PATH);

    System.setProperty("webdriver.chrome.whitelistedIps", "");
    //브라우저 열 때 옵션
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(true);
//    options.setBinary("/usr/bin/chromium-browser");
//    options.addArguments("--remote-debugging-address=" + containerIp);
//    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--lang=ko");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--disable-popup-blocking");       //팝업안띄움
    options.addArguments("--headless");                       //브라우저 안띄움
    options.addArguments("--disable-gpu");      //gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
    options.setCapability("ignoreProtectedModeSettings", true);

    //브라우저 선택
    driver = new ChromeDriver(options);

    //로직
    try {
      isConfirmedEdu = checkEmail(email);
    } catch (Exception e) {
      //todo: 셀레니움 예외처리
    }

    CertifiedEduResponse certifiedEduResponse = null;
    String key = "";
    if (isConfirmedEdu) { //에듀싸피에 유효한 이메일이면 메일을 보낸다.
      key = createKey();
      //key db에 email이랑 같이 저장해주기
      Certification initCertification = Certification.init(email, key, true, false);
      boolean confirmedEdu = initCertification.isConfirmedEdu();
      boolean confirmedCode = initCertification.isConfirmedCode();
      String code = initCertification.getCode();
      Optional<Certification> optionalCertification = certificationRepository.findByEmail(email);
      if (optionalCertification.isPresent()) { //존재할 때는 update 해야한다.
        Certification certification = optionalCertification.get();
        certification.update(code, confirmedEdu, confirmedCode);
      } else { //없으면 저장해줘야한다.
        certificationRepository.save(initCertification); //edu 만 true인 상태
      }
      //이메일 검증이 들어오면 항상 init 상태로 반환하면 된다.
      certifiedEduResponse = new CertifiedEduResponse(confirmedEdu, code);
      try {
        sendSimpleMessage(email, key);
      } catch (Exception e) {
        //todo: email 예외처리
      }
    } else {
      certifiedEduResponse = new CertifiedEduResponse(false, key);
    }

    driver.close(); //탭 닫기
    driver.quit(); //브라우저 닫기
    return certifiedEduResponse;
  }

  private boolean checkEmail(String email) throws InterruptedException {
    driver.get(URL);

    // 스크립트를 사용하기 위한 캐스팅
    JavascriptExecutor js = (JavascriptExecutor) driver;

    //css id가 userId로 돼있는 email input 창 가져오기
    WebElement userId = driver.findElement(
        By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[1]/div[1]/input"));
    userId.sendKeys(email); //email 입력하기

    // css id가 userPwd로 돼있는 비밀번호 input 창 가져오기
    WebElement userPwd = driver.findElement(
        By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[1]/div[2]/input"));
    userPwd.sendKeys("1"); //1 입력하기 => 틀리기 위해

    //css class가 btn-primary로 돼있는 "로그인" 버튼 가지고오기
    WebElement loginButton = driver.findElement(
        By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[3]/a"));

    // 클릭한다. 사실 element.click()로도 클릭이 가능한데 가끔 호환성 에러가 발생하는 경우가 있다.
    js.executeScript("arguments[0].click();", loginButton);

//    loginButton.click(); //버튼 클릭하기
//    Thread.sleep(1000); //1초 정지

    WebElement messageBox = driver.findElement(By.className("c-desc"));
    String message = messageBox.getText();
    log.info(message);
    boolean result = false;
    switch (message) {
      case "비밀번호가 일치하지 않습니다.":
        log.info("등록돼있는 유저");
        result = true;
        break;
      case "등록된 사용자 정보가 없습니다.":
        log.info("등록안된 유저");
        result = false;
        break;
      //이 아래는 나오면 안되는 값이다.
      case "[아이디]은(는) 필수값입니다.":
        result = false;
        break;
      case "[비밀번호]은(는) 필수값입니다.":
        result = false;
        break;
      default:
        result = false;
        break;
    }
    return result;
  }

  /**
   * desc 로그인요청시 응답
   *
   * @param signInUserRequest
   * @return
   */
  @Override
  public SignInUserResponse signIn(SignInUserRequest signInUserRequest) {
    Optional<User> user = userRepository.findByEmailWithAreaAndAnonymousProfileImage(
        signInUserRequest.getEmail());

    if (user.isPresent()) {
      User findUser = user.get();
      String salt = findUser.getSalt();
      String encrypt = Encrypt.getEncrypt(signInUserRequest.getPassword(), salt);
      // 다르넹...
      // 패스워드 일치 확인
      if (findUser.getPassword().equals(encrypt)) {
        return new SignInUserResponse(findUser);
      }
      return null;
    }
    return null;
  }

  private String createKey() {
    StringBuffer key = new StringBuffer();
    Random rnd = new Random();

    for (int i = 0; i < 8; i++) { // 인증코드 8자리
      int index = rnd.nextInt(3); // 0~2 까지 랜덤

      switch (index) {
        case 0:
          key.append((char) ((int) (rnd.nextInt(26)) + 97));
          //  a~z  (ex. 1+97=98 => (char)98 = 'b')
          break;
        case 1:
          key.append((char) ((int) (rnd.nextInt(26)) + 65));
          //  A~Z
          break;
        case 2:
          key.append((rnd.nextInt(10)));
          // 0~9
          break;
      }
    }
    return key.toString();
  }

  private MimeMessage createMessage(String to, String ePw) throws Exception {
    System.out.println("보내는 대상 : " + to);
    System.out.println("인증 번호 : " + ePw);
    MimeMessage message = emailSender.createMimeMessage();

    message.addRecipients(RecipientType.TO, to);//보내는 대상
    message.setSubject("fri 이메일 확인 코드입니다.");//제목

    String msgg = "";
    msgg += "<div style='margin:20px;'>";
    msgg += "<h1> 안녕하세요 fri입니다. </h1>";
    msgg += "<br>";
    msgg += "<p>아래 코드를 복사해 입력해주세요<p>";
    msgg += "<br>";
    msgg += "<p>감사합니다.<p>";
    msgg += "<br>";
    msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
    msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
    msgg += "<div style='font-size:130%'>";
    msgg += "CODE : <strong>";
    msgg += ePw + "</strong><div><br/> ";
    msgg += "</div>";
    message.setText(msgg, "utf-8", "html");//내용
    message.setFrom(new InternetAddress("no.reply.eggfri@gmail.com", "fri"));//보내는 사람

    return message;
  }

  public void sendSimpleMessage(String to, String ePw) throws Exception {
    MimeMessage message = createMessage(to, ePw);
    try {//예외처리
      emailSender.send(message);
    } catch (MailException es) {
      es.printStackTrace();
//      throw new IllegalArgumentException();
    }
  }

  @Transactional
  public CertifiedCodeResponse certifiedCode(CertifiedCodeRequest certifiedCodeRequest) {
    String code = certifiedCodeRequest.getCode();
    String email = certifiedCodeRequest.getEmail();
    CertifiedCodeResponse certifiedCodeResponse = null;
    Certification certification = certificationRepository.findByEmail(email)
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_CERTIFICATION));
    boolean confirmedEdu = certification.isConfirmedEdu();
    String certificationCode = certification.getCode();
    if (certificationCode.equals(code)) { //코드가 일치할 때
      certification.update(code, confirmedEdu, true); //true로 업데이트
      certifiedCodeResponse = new CertifiedCodeResponse(true);
    } else {//일치하지 않을 때 아무동작 안한다.
      certifiedCodeResponse = new CertifiedCodeResponse(false);
    }
    return certifiedCodeResponse;
  }

  /**
   * desc 유저정보 조회
   *
   * @param userId
   * @return
   */
  @Override
  public FindUserResponse findUser(Long userId) {
    Optional<User> user = userRepository.findByIdWithAreaAndAnonymousProfileImage(userId);
    User findUser = user.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    return new FindUserResponse(findUser);
  }

  /**
   * 유저 프로필수정 (닉네임, 사진)
   *
   * @param updateUserProfileRequest 프로필 수정 요청
   * @param userId                   수정할 유저
   * @return 프로필 수정 응답
   */
  @Override
  @Transactional
  public UpdateUserProfileResponse updateUserProfile(
      UpdateUserProfileRequest updateUserProfileRequest, Long userId) {
    User userNickName = userRepository.findByNickname(updateUserProfileRequest.getNickname())
        .orElse(null);

    // 중복된 닉네임 유저 있는지 확인
    if (userNickName != null && !userNickName.getId().equals(userId)) {
      return null;
    }

    User findUser = userRepository.findByIdWithAnonymousProfileImage(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Optional<AnonymousProfileImage> anonymousProfileImage = anonymousProfileImageRepository.findById(
        updateUserProfileRequest.getAnonymousProfileImageId());
    AnonymousProfileImage findAnonymousProfileImage = anonymousProfileImage.orElseThrow(
        () -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_AnonymousProfileImage));

    User updateUser = findUser.updateUserProfile(findAnonymousProfileImage,
        updateUserProfileRequest.getNickname());

    return UpdateUserProfileResponse.builder()
        .anonymousProfileImageUrl(updateUser.getAnonymousProfileImage().getImageUrl())
        .nickname(updateUser.getNickname())
        .build();
  }

  @Override
  public HttpStatus certifiedNickname(CertifiedNicknameRequest certifiedNicknameRequest) {
    String nickname = certifiedNicknameRequest.getNickname();
    Optional<User> optionalUser = userRepository.findByNickname(nickname);
    HttpStatus returnStatus = null;
    if (optionalUser.isPresent()) { //해당 닉네임 유저가 존재한다.
      returnStatus = HttpStatus.BAD_REQUEST;
    } else { //해당 닉네임 유저가 존재하지 않는다.
      returnStatus = HttpStatus.OK;
    }
    return returnStatus;
  }

  @Override
  @Scheduled(cron = "0 0 0 1/1 * ?")
  @Transactional
  public void updateUserHeartScheduler() {
    List<User> findUserList = userRepository.findByHeartLessThanEqual(4);
    for (User u : findUserList) {
      u.plusHeart();
    }
  }

  /**
   * 유저 프로필 수정을 위한 사진 리스트 조회
   *
   * @return FindAnonymousProfileImagesResponse
   */
  @Override
  public FindAnonymousProfileImagesResponse findAnonymousProfileImages() {
    List<AnonymousProfileImage> findImages = anonymousProfileImageRepository.findAll();
    List<FindAnonymousProfileImageDto> result = findImages.stream()
        .map(i -> FindAnonymousProfileImageDto.builder()
            .anonymousImageId(i.getId())
            .anonymousImageUrl(i.getImageUrl())
            .build())
        .collect(Collectors.toList());
    return new FindAnonymousProfileImagesResponse(result);
  }

  @Override
  @Transactional
  public UpdateIsReadyResponse updateIsReady(Long userId,
      UpdateIsReadyRequest updateIsReadyRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    GameRoom gameRoom = gameRoomRepository.findById(updateIsReadyRequest.getGameRoomId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_GAME_ROOM));

    user.updateReady(updateIsReadyRequest.isReady());

    int value = 0;
    //todo: 프론트를 믿지말고, postman으로 true가 계속 넘어오면 계속 +1 한다. => 고치기
    if(user.isReady()){ //준비
      value = 1;
    }else{ //준비 해제
      value = -1;
    }

    int updateReadyCount = gameRoom.updateReadyCount(value);
    //headCount를 채우지 않아도 동작하도록 하는 코드
//    List<User> allByGameRoom = userRepository.findAllByGameRoom(gameRoom);
//    int size = allByGameRoom.size();

    //headCount를 모두 채워야 동작하는 코드
    int headCount = gameRoom.getHeadCount();
    if(updateReadyCount == headCount){ //방 인원과 readyCount가 동일하면
      SocketGameRoomStatusRequestAndResponse x = new SocketGameRoomStatusRequestAndResponse();
      messagingTemplate.convertAndSend("/sub/game-room/ready/" + gameRoom.getId(), true);
    }

    UpdateIsReadyResponse updateIsReadyResponse = UpdateIsReadyResponse.create(user.isReady());
    return updateIsReadyResponse;
  }
}
