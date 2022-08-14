package com.stayaway.dao.repository;

import com.stayaway.dao.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {
    Optional<Board> findFirstByGameIdOrderByStageDesc(String boardId);
}
