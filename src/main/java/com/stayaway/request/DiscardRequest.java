package com.stayaway.request;

import com.stayaway.model.cards.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscardRequest {
    private CardType cardType;
}
