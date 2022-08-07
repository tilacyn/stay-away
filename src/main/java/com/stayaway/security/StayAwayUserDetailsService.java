package com.stayaway.security;

import com.stayaway.dao.UserDAO;
import com.stayaway.exception.StayAwayException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class StayAwayUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public StayAwayUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws StayAwayException {
        com.stayaway.dao.model.User user;
        String password;
        user = userDAO.getUser(username);
        password = userDAO.getPassword(username);

        return User.withDefaultPasswordEncoder()
                .username(user.getLogin())
                .password(password)
                .roles("USER")
                .build();
    }
}
