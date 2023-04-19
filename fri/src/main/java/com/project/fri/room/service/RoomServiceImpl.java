package com.project.fri.room.service;

import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.fri.room.service fileName       : RoomServiceImpl date           :
 * 2023-04-18 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Room> findAllByArea(String areaString) {
//    return roomRepository.findAllByArea(area)
//        .orElseThrow(
//            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_LIST));
    return null;
  }
}
