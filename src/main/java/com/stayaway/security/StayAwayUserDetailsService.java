package com.stayaway.security;

import com.stayaway.dao.repository.UserRepository;
import com.stayaway.exception.StayAwayException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class StayAwayUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public StayAwayUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws StayAwayException {
        com.stayaway.dao.model.User user;
        String password;
        user = userRepository.findByLogin(login).orElseThrow(() -> StayAwayException.notFound(login,
                StayAwayException.EntityType.USER));
        password = "123456";

        return User.withDefaultPasswordEncoder()
                .username(user.getLogin())
                .password(password)
                .roles("USER")
                .build();
    }
}
