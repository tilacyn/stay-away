package com.stayaway.dao.repository;

import java.util.Optional;

import com.stayaway.dao.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);

}
