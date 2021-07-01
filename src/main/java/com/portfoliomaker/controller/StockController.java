package com.portfoliomaker.controller;

import com.portfoliomaker.service.SeleniumService;
import com.portfoliomaker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/stock")
public class StockController {
    @Autowired
    StockService stockService;
    @Autowired
    SeleniumService seleniumService;

    @GetMapping("/sync")
    @ResponseBody
    public void sync() {
        seleniumService.doProcess();
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("stocks", stockService.findAll());
        return "stock/main";
    }
    @DeleteMapping("")
    @ResponseBody
    public void delete(String ticker){
        stockService.delete(ticker);
    }


    @PostMapping("")
    @ResponseBody
    public void addStock(String ticker, int count) {
        stockService.save(ticker, count);
    }
}