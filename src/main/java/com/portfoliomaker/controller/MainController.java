package com.portfoliomaker.controller;

import com.portfoliomaker.entity.Main;
import com.portfoliomaker.service.MainService;
import com.portfoliomaker.service.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    MainService mainService;
    @Autowired
    SeleniumService seleniumService;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("hello", "hello");
        List<Main> list = mainService.findAll();
        model.addAttribute("mains", list);
        return "main/main";
    }

    @GetMapping("/index.html")
    public String index(Model model) {
        return "main/main";
    }

    @GetMapping("sync")
    public String sync() {
        mainService.deleteAll();
        Main main = new Main();
        main.id = "123";
        main.value = "1";
        mainService.save(main);
        seleniumService.doProcess();
        return "redirect:";
    }
}