package com.stayaway.dao;


import com.stayaway.dao.model.User;
import com.stayaway.exception.StayAwayException;

public interface UserDAO {
    User getUser(String id) throws StayAwayException;

    String getPassword(String id) throws StayAwayException;
}
