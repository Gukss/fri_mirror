package com.project.fri.room.repository;

import com.project.fri.room.entity.Room;
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

}
