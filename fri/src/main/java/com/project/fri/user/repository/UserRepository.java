package com.project.fri.user.repository;

import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.project.fri.user.repository fileName       : UserRepository date           :
 * 2023-04-18 description    :
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
