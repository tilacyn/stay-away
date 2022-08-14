package com.stayaway.model.actions;

import lombok.Data;

@Data
public class TargetChosenAction {
    private final String chooser;
    private final String target;
}
