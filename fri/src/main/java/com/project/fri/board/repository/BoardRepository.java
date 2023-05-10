package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BoardRepository extends JpaRepository<Board, Long> {

}
