package com.stayaway.dao.repository;

import com.stayaway.dao.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
}
