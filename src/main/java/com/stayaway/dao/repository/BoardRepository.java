package com.stayaway.dao.repository;

import com.stayaway.dao.model.BoardState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<BoardState, String> {
    Optional<BoardState> findFirstByGameIdOrderByStageDesc(String boardId);
}
