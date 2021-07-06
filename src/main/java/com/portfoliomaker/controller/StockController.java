package com.portfoliomaker.controller;

import com.portfoliomaker.dto.stock.StockDetailDTO;
import com.portfoliomaker.dto.stock.StockPortfolioDTO;
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

    @GetMapping("/stockSync")
    @ResponseBody
    public void stockSync() {
        stockService.stockSync();
    }

    @GetMapping("/p2pSync")
    @ResponseBody
    public void p2pSync() {
        stockService.p2pSync();
    }

    @GetMapping("/portfolioSync")
    @ResponseBody
    public void portfolioSync() {
        stockService.portfolioSync();
    }

    @GetMapping("/main")
    public String main(Model model, @RequestParam(value = "location", required = false) String location) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO(location));
        return "stock/main";
    }


    @GetMapping("/favorite/k")
    public String favoriteK(Model model) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO("코스피"));
        return "stock/favorite_k";
    }

    @GetMapping("/main/k")
    public String mainK(Model model) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO("코스피"));
        return "stock/main_k";
    }


    @GetMapping("/favorite/list")
    @ResponseBody
    public List<StockPortfolioDTO> favoriteList(@RequestParam(value = "location", required = false) String location) {
        return stockService.getAllStockPortfolioDTO(location);
    }


    @GetMapping("/main/list")
    @ResponseBody
    public List<StockPortfolioDTO> mainList(@RequestParam(value = "location", required = false) String location) {
        return stockService.getAllStockPortfolioDTO(location);
    }


    @GetMapping("/detail")
    public String detail(Model model) {
        return "stock/detail";
    }


    @GetMapping("/detail/list")
    @ResponseBody
    public List<StockDetailDTO> detailList(@RequestParam(value = "ticker", required = false) String ticker) {
        return stockService.findDetailList(ticker);
    }


    @GetMapping("/meta")
    public String meta(Model model) {
        model.addAttribute("metas", stockService.getAllMetas());
        return "stock/meta";
    }

    @DeleteMapping("/meta")
    @ResponseBody
    public void deleteMeta(String id) {
        stockService.deleteMeta(id);
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