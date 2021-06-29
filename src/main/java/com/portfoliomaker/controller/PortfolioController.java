package com.portfoliomaker.controller;

import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping()
    public String addPortfolio(String name, long price) {
        portfolioService.save(new Portfolio(name, price));
        return "redirect:";
    }

    @GetMapping("/detail")
    public String detail(Model model) {
        model.addAttribute("details", portfolioService.getDetail());
        return "portfolio/detail";
    }

    @DeleteMapping()
    @ResponseBody
    public void delete(String id) {
        portfolioService.deleteById(id);
    }
}