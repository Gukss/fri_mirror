package com.project.fri.scrap.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByUserAndBoardAndIsDeleteFalse(User user, Board board);
}
