package com.project.fri.scrap.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByUserAndBoardAndIsDeleteFalse(User user, Board board);

    @Query("SELECT s FROM Scrap s JOIN FETCH s.board WHERE s.user = :user AND s.isDelete =false ORDER BY s.createdAt ASC")
    List<Scrap> findAllByUserAndIsDeleteFalseOrderByCreatedAtAscWithBoard(@Param("user") User user);

}
