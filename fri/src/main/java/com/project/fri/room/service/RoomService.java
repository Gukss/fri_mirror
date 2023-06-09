package com.project.fri.room.service;

import com.project.fri.common.entity.Category;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.dto.FindAllRoomByCategoryResponse;

import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.dto.FindAllRoomResponse;
import java.util.List;

import com.project.fri.room.dto.CreateRoomRequest;
import org.springframework.data.domain.Pageable;

/**
 * packageName    : com.project.fri.room.service fileName       : RoomService date           :
 * 2023-04-18 description    :
 */
public interface RoomService {

  /**
   * 방 생성
   *
   * @param request
   * @return 만든 방 제목
   */
  CreateRoomResponse createRoom(CreateRoomRequest request, Long userId);

  /**
   * desc: 전체 방 리스트를 조회, area에 따라 해당 지역의 방 리스트를 조회한다.
   * @param areaString
   * @return 카테고리 별로 묶여있는 전체 방 리스트
   */
//  List<Room> findAllByArea(String areaString);

  /**
   * 방 더보기
   *
   * @return 카테고리 별 방 목록 더보기
   */
  List<FindAllRoomByCategoryResponse> findAllByAreaAndRoomCategory(Category area,
      com.project.fri.room.entity.Category roomCategory, int page,
      Pageable pageable, Long userId);

  FindAllRoomResponse findAllByArea(Category areaString, Long userId);

  /**
   * desc: 요청한 방 한개에 대한 상세 정보 조회
   *
   * @param roomId 찾으려는 방 Id
   * @return 요청한 방에 대한 상세 정보
   */
  FindRoomResponse findRoom(Long roomId, Long userId);

  /**
   * 매주 금요일 23시 11분 방 삭제 처리
   */
  void deleteRoomScheduler();
}
