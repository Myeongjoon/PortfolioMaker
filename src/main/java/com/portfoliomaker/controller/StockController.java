package com.portfoliomaker.controller;

import com.portfoliomaker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/stock")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("stocks", stockService.findAll());

        return "stock/main";
    }
}