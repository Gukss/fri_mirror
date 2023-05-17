package com.project.fri.gameChatting.repository;

import com.project.fri.gameChatting.entity.GameChattingMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameChattingRepository extends JpaRepository<GameChattingMessage,Long>,GameChattingRepositoryCustom {

}
