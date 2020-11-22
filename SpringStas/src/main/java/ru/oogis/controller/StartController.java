package ru.oogis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.oogis.model.SubjectEnum;

@Controller
public class StartController {


    /**
     * Opens a page where some commands and descriptions for them are written.
     */
    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("enum", SubjectEnum.values());
        return "hello";
    }


}
