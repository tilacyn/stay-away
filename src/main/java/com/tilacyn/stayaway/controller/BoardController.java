package com.tilacyn.stayaway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable String boardId, Model model) {
        return "boardPage";
    }
}
