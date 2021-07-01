package com.portfoliomaker.controller;

import com.portfoliomaker.service.PortfolioService;
import com.portfoliomaker.service.SeleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    SeleniumService seleniumService;
    @Autowired
    PortfolioService portfolioService;

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("portfolios", portfolioService.findAll());
        return "main/main";
    }

    @GetMapping("/index.html")
    public String index(Model model) {
        return "main/main";
    }
}