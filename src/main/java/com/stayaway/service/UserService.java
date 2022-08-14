package com.stayaway.service;

import com.stayaway.dao.model.User;
import com.stayaway.dao.repository.UserRepository;
import com.stayaway.exception.StayAwayException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    TODO remove when signup implemented
    @PostConstruct
    private void saveUsersOnInit() {
        save(new User("tilacyn"));
        save(new User("Kowalski1337"));
        save(new User("hey_boris"));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
