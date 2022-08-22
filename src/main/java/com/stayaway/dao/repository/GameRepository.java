package com.stayaway.dao.repository;

import com.stayaway.dao.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {

}
