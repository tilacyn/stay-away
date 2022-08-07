package com.stayaway.service;

import com.stayaway.dao.model.Board;
import com.stayaway.dao.model.User;
import com.stayaway.dao.repository.UserRepository;
import com.stayaway.exception.StayAwayException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getBoardPlayers(Board board) {
        return userRepository.usersListByIds(
                board.getPlayers()
                        .stream()
                        .map(Board.Player::getUserId)
                        .collect(Collectors.toList()));
    }

    public User getUser(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> StayAwayException.notFound(login, StayAwayException.EntityType.USER));
    }
}
