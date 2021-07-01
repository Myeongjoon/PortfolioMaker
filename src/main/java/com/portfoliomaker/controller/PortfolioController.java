package com.portfoliomaker.controller;

import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
        portfolioService.save(name, price);
        return "redirect:";
    }

    @GetMapping("/portfolio")
    public String portfolio(Model model) {
        model.addAttribute("portfolios", portfolioService.getPortfolio());
        return "portfolio/portfolio";
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(value = "name", required = false) String name) {
        model.addAttribute("details", portfolioService.getDetail(name));
        return "portfolio/detail";
    }

    @DeleteMapping()
    @ResponseBody
    public void delete(String name) {
        portfolioService.deleteById(name);
    }

    @DeleteMapping("/detail")
    @ResponseBody
    public void deleteDetail(String id) {
        portfolioService.deleteDetail(id);
    }
}