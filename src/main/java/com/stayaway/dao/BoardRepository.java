package com.stayaway.dao;

import com.stayaway.model.BoardModel;
import org.springframework.stereotype.Component;

@Component
public class BoardRepository implements BoardDAO {

    @Override
    public BoardModel getBoard(String id) {
        return null;
    }
}
