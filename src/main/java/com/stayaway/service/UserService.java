package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.dao.repository.UserRepository;
import com.stayaway.exception.StayAwayException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<User> getBoardPlayers(Board board) {
        return userRepository.usersListByIds(
                board.getPlayers()
                        .stream()
                        .map(Board.Player::getLogin)
                        .collect(Collectors.toList()));
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> StayAwayException.notFound(login, StayAwayException.EntityType.USER));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
