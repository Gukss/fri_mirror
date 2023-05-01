package com.project.fri.user.repository;

import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.project.fri.user.repository fileName       : UserRepository date           :
 * 2023-04-18 description    :
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByGameRoom_Id(Long id);  // list는 null이면 빈배열로 넘어와서 optional로 안감싸줘도 된다.
    List<User> findAllByRoom(Room room);

}
