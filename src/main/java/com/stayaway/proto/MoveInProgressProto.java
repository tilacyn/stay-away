package com.stayaway.proto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoveInProgressProto {
    private MoveStageProto moveStage;
    private String firstUserID;
//  should be empty if only one user is involved
//  in case of MoveStageProto == SHOWING_CARDS and this being empty
//  all other player are supposed to see the firstPlayer's cards
    private String secondUserID;
}
