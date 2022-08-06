package com.stayaway.dao;


import com.stayaway.exception.UserNotFoundException;
import com.stayaway.model.User;

public interface UserDAO {
    User getUser(String id) throws UserNotFoundException;

    String getPassword(String id) throws UserNotFoundException;
}
