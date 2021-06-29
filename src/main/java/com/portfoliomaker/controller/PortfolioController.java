package com.portfoliomaker.controller;

import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/portfolio")
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;

    @GetMapping()
    @ResponseBody
    public List<Portfolio> getList() {
        return portfolioService.findAll();
    }
}