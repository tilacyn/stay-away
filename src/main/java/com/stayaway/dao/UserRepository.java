package com.stayaway.dao;

import com.stayaway.exception.UserNotFoundException;
import com.stayaway.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository implements UserDAO {
    private final static Map<String, User> USERS = new HashMap<>();
    private final static Map<String, String> PASSWORDS = new HashMap<>();

    static {
        USERS.put("Kowalski1337", new User("Kowalski1337"));
        USERS.put("tilacyn", new User("tilacyn"));
        PASSWORDS.put("Kowalski1337", "123456");
        PASSWORDS.put("tilacyn", "123456");
    }

    @Override
    public User getUser(String id) throws UserNotFoundException {
        return Optional.ofNullable(USERS.get(id))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public String getPassword(String id) throws UserNotFoundException {
        return Optional.ofNullable(PASSWORDS.get(id))
                .orElseThrow(UserNotFoundException::new);
    }


}
