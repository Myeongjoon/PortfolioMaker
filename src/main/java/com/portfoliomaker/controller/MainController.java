package com.portfoliomaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("hello", "hello");
        return "main/main";
    }

    @GetMapping("sync")
    public String sync() {
        return "main/main";
    }
}