package com.portfoliomaker.controller;

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

    @GetMapping("/stockSync")
    @ResponseBody
    public void stockSync() {
        stockService.stockSync();
    }

    @GetMapping("/portfolioSync")
    @ResponseBody
    public void portfolioSync() {
        stockService.portfolioSync();
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO());
        return "stock/main";
    }


    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(value = "ticker", required = false) String ticker) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO());
        return "stock/detail";
    }


    @GetMapping("/meta")
    public String meta(Model model) {
        model.addAttribute("metas", stockService.getAllMetas());
        return "stock/meta";
    }

    @DeleteMapping("")
    @ResponseBody
    public void delete(String ticker) {
        stockService.delete(ticker);
    }


    @PostMapping("")
    @ResponseBody
    public void addStock(String ticker, int count) {
        stockService.save(ticker, count);
    }
}