package com.portfoliomaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/stock")
public class StockController {

    @GetMapping("/main")
    public String main(String name) {
        return "stock/main";
    }
}