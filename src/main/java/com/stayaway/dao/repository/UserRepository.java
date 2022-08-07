package com.stayaway.dao.repository;

import com.stayaway.dao.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);

    default List<User> usersListByIds(List<String> ids) {
        return StreamSupport.stream(findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

}
