package com.stayaway.request;

import com.stayaway.model.cards.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayRequest {
    private CardType cardType;
    private String target;
}
