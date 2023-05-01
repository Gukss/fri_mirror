package com.project.fri.user.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.dto.CertifiedUserRequest;
import com.project.fri.user.dto.CreateUserRequest;
import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
import com.project.fri.user.dto.UpdateUserReadyResponse;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.project.fri.user.service fileName       : UserServiceImpl date           :
 * 2023-04-19 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final RoomRepository roomRepository;
  private final AreaRepository areaRepository;
  private final UserRepository userRepository;

  private WebDriver driver;

  private static final String URL = "https://edu.ssafy.com/comm/login/SecurityLoginForm.do";
  private static final String LOCAL_PATH = "D:\\Guk\\fri\\chromedriver.exe";
  private static final String SERVER_PATH = "/home/ubuntu/chromedriver";

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
    log.info("findUser.getRoom(): "+findUser.getRoom());
    if (findUser.getRoom() == null) {
      // 해당 유저가 어떤방에도 입장하지 않은 상태일 때 -> 바로 입장
      if (findUser.getHeart() >= 1) { //하트 1이상일 때
        if (Boolean.FALSE.equals(request.getIsParticipate())) { //false로 오면 참여하기를 누른거다.
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
        request.getIsParticipate())) {
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

  public UpdateUserReadyResponse updateUserReady(long userId, long roomId) {
    //todo: 처음에 pathVariable로 받아온 roomId와 user에서 꺼내온 roomId를 비교한다.
    // 비교하고 다르면 404를 던진다?
    User foundUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    Room room = foundUser.getRoom();
    Long foundUserRoomId = room.getId();
//    boolean curReady = foundUser.isReady(); //현재 ready 상태
//    boolean curConfirmed = room.isConfirmed();
    UpdateUserReadyResponse updateUserReadyResponse = null; //반환할 객체 미리 선언하기

    if (foundUserRoomId == roomId) { //같을 때 => 정상 진행
      //해당 방에 속한 user 찾아와서 본인 빼고 모두 ready 눌렀는지 확인하기
      List<User> userList = userRepository.findAllByRoom(room);
      boolean isAllReady = true;
      //todo: 본인은 반복문에서 검사를 안하는데
      // 본인이 true인 경우 다시 false로 바꿔주면 안되기 때문에
      // 적절한 조치가 필요해보인다.
      for (User x : userList) {
        log.info("userID: " + x.getId());
        if (x.equals(foundUser)) { //본인이면
          continue;
        }
//        if(!x.isReady()){ //ready가 안됐으면 return => 다른 사람들이 ready하도록 기다려야 한다.
//          isAllReady = false;
//          break;
//        }
      }
      //일단 나의 ready를 not해서 바꿔준다.
//      boolean nextReady = !curReady;
//      boolean nextConfirmed = curConfirmed;
//
//      foundUser.updateReady(nextReady);
//      if(isAllReady){
//        //isAllReady가 true면 나 빼고 모두 완료했다는 의미다. => 현재 방의 isConfirmed를 바꿔준다.
//        nextConfirmed = !curConfirmed;
//        room.updateIsConfirmed(nextConfirmed);
//      }

      updateUserReadyResponse = UpdateUserReadyResponse.builder()
//          .ready(nextReady)
//          .isConfirmed(nextConfirmed)
          .roomId(roomId)
          .build();

    } else { //여기로 넘어오면 비정상적인 요청이다.
      //todo: 반환 객체 updateUserReadyResponse 에 error 담아서 보내기?
    }
    return updateUserReadyResponse;
  }

  /**
   * desc: 회원가입
   *
   * @return
   */
  @Override
  public void createUser(CreateUserRequest request) {

    // area 찾기
    Area area = areaRepository.findByCategory(request.getArea())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    // user db에 저장
    User user = User.create(request, area);
    User saveUser = userRepository.save(user);
  }

  /**
   * desc: 셀레니움을 이용한 유저 이메일 검증
   * @param certifiedUserRequest
   */
  @Override
  public boolean certifiedUser(CertifiedUserRequest certifiedUserRequest) {
    boolean result = false;

    //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)
//    System.setProperty("webdriver.chrome.driver", LOCAL_PATH);
    System.setProperty("webdriver.chrome.driver", SERVER_PATH);

    System.setProperty("webdriver.chrome.whitelistedIps", "");
    //브라우저 열 때 옵션
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(true);
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--lang=ko");
    options.addArguments("--disable-popup-blocking");       //팝업안띄움
    options.addArguments("headless");                       //브라우저 안띄움
    options.addArguments("--disable-gpu");			//gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
    options.setCapability("ignoreProtectedModeSettings", true);

    //브라우저 선택
    driver = new ChromeDriver(options);

    //로직
    try{
      result = checkEmail(certifiedUserRequest.getEmail());
    }catch(Exception e){
      //셀레니움 예외처리
    }

    driver.close(); //탭 닫기
    driver.quit(); //브라우저 닫기
    return result;
  }

  private boolean checkEmail(String email) throws InterruptedException {
    driver.get(URL);

    // 스크립트를 사용하기 위한 캐스팅
    JavascriptExecutor js = (JavascriptExecutor) driver;

    //css id가 userId로 돼있는 email input 창 가져오기
    WebElement userId = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[1]/div[1]/input"));
    userId.sendKeys(email); //email 입력하기

    // css id가 userPwd로 돼있는 비밀번호 input 창 가져오기
    WebElement userPwd = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[1]/div[2]/input"));
    userPwd.sendKeys("1"); //1 입력하기 => 틀리기 위해

    //css class가 btn-primary로 돼있는 "로그인" 버튼 가지고오기
    WebElement loginButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/form/div/div[2]/div[3]/a"));

    // 클릭한다. 사실 element.click()로도 클릭이 가능한데 가끔 호환성 에러가 발생하는 경우가 있다.
    js.executeScript("arguments[0].click();", loginButton);

//    loginButton.click(); //버튼 클릭하기
//    Thread.sleep(1000); //1초 정지

    WebElement messageBox = driver.findElement(By.className("c-desc"));
    String message = messageBox.getText();
    log.info(message);
    boolean result = false;
    switch(message){
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
}
