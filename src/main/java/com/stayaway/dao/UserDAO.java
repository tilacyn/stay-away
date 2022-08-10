package com.stayaway.dao;


import com.stayaway.exception.StayAwayException;
import com.stayaway.dao.model.User;

public interface UserDAO {
    User getUser(String id) throws StayAwayException;

    String getPassword(String id) throws StayAwayException;
}
