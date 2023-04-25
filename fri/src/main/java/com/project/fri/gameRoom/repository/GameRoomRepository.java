package com.project.fri.gameRoom.repository;

import com.project.fri.gameRoom.entity.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.gameRoom.repository fileName       : GameRoomRepository author
 *       : hagnoykmik date           : 2023-04-25 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-25        hagnoykmik       최초 생성
 */
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

}
