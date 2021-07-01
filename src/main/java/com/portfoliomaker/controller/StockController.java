package com.portfoliomaker.controller;

import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.service.SeleniumService;
import com.portfoliomaker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/stock")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/sync")
    @ResponseBody
    public void sync() {
        stockService.sync();
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO());
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