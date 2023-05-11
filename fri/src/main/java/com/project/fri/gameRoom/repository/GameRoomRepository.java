package com.project.fri.gameRoom.repository;

import com.project.fri.common.entity.Area;
import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.room.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.project.fri.gameRoom.repository fileName       : GameRoomRepository author
 *       : hagnoykmik date           : 2023-04-25 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-25        hagnoykmik       최초 생성
 */
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {
    List<GameRoom> findByAreaAndIsDeleteFalse(Area area, Pageable pagable);
    List<GameRoom> findByAreaAndIsDeleteFalseOrderByCreatedAtDesc(Area area);
}
