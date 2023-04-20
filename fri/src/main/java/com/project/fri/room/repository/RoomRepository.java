package com.project.fri.room.repository;

import com.project.fri.common.entity.Area;
import com.project.fri.room.entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.project.fri.room.repository
 * fileName       : RoomRepository
 * date           : 2023-04-18
 * description    :
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  Optional<List<Room>> findAllByArea(Area area);
}
