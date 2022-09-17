package com.stayaway.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//todo STAYAWAY-40
public class PlayRequest {
    private int card;
    private String target;
}
