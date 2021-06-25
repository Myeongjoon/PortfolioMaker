package com.portfoliomaker.controller;

import com.portfoliomaker.entity.Main;
import com.portfoliomaker.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("hello", "hello");
        model.addAttribute("main", mainService.findAll());
        return "main/main";
    }

    @GetMapping("sync")
    public String sync() {
        mainService.deleteAll();
        mainService.save(new Main());
        return "main/main";
    }
}