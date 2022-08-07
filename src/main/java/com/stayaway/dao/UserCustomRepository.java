package com.stayaway.dao;

import com.stayaway.dao.model.User;
import com.stayaway.exception.StayAwayException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserCustomRepository implements UserDAO {
    private final static Map<String, User> USERS = new HashMap<>();
    private final static Map<String, String> PASSWORDS = new HashMap<>();

    static {
        USERS.put("Kowalski1337", new User("", "Kowalski1337"));
        USERS.put("tilacyn", new User("", "tilacyn"));
        PASSWORDS.put("Kowalski1337", "123456");
        PASSWORDS.put("tilacyn", "123456");
    }

    @Override
    public User getUser(String login) throws StayAwayException {
        return Optional.ofNullable(USERS.get(login))
                .orElseThrow(() -> StayAwayException.notFound(login, StayAwayException.EntityType.USER));
    }

    @Override
    public String getPassword(String login) throws StayAwayException {
        return Optional.ofNullable(PASSWORDS.get(login))
                .orElseThrow(() -> StayAwayException.notFound(login, StayAwayException.EntityType.USER));
    }


}
