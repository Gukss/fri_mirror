package com.project.fri.room.repository;

import com.project.fri.common.entity.Area;
import com.project.fri.room.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.project.fri.room.repository
 * fileName       : RoomRepository
 * date           : 2023-04-18
 * description    :
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  @Query("SELECT r FROM Room r JOIN FETCH r.roomCategory WHERE r.id = :roomId")
  Optional<Room> findRoomWithCategoryById(@Param("roomId") Long roomId);

  Optional<List<Room>> findAllByArea(Area area);

}
