package com.project.fri.user.repository;


import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import com.project.fri.user.entity.UserRoomTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomTimeRepository extends JpaRepository<UserRoomTime,Long> {
  Optional<UserRoomTime> findByUserAndRoomAndIsDeleteFalse(User user, Room room);
}
