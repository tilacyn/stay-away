package com.stayaway.security;

import com.stayaway.dao.UserDAO;
import com.stayaway.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class StayAwayUserDetailsService implements UserDetailsService {
    private UserDAO userDAO;

    public StayAwayUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.stayaway.model.User user;
        String password;
        try {
            user = userDAO.getUser(username);
            password = userDAO.getPassword(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("user not found in UserDAO", e);
        }

        return User.withDefaultPasswordEncoder()
                .username(user.getId())
                .password(password)
                .roles("USER")
                .build();
    }
}
