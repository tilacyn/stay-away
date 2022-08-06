package com.stayaway.input;

import lombok.Data;

import java.util.List;

@Data
public class CreateGameInput {
    private List<String> userIDs;
    private String name;
}
