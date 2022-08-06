package com.stayaway.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameProto {
    String id;
    String name;
    List<String> userIDs;
}
