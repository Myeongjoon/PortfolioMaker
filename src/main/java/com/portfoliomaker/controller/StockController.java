package com.portfoliomaker.controller;

import com.portfoliomaker.dto.stock.StockDetailDTO;
import com.portfoliomaker.dto.stock.StockFavoriteDTO;
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
    public void stockSync(String location) {
        stockService.stockSync(location);
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

    @GetMapping("/portfolio")
    public String portfolio(Model model, @RequestParam(value = "location", required = false) String location) {
        model.addAttribute("stocks", stockService.getAllStockPortfolioDTO(location));
        return "stock/portfolio";
    }

    @GetMapping("/favorite/k")
    public String favoriteK(Model model) {
        return "stock/favorite_k";
    }

    @GetMapping("/favorite/n")
    public String favoriteN(Model model) {
        return "stock/favorite_n";
    }

    @GetMapping("/portfolio/k")
    public String portfolioK(Model model) {
        return "stock/portfolio_k";
    }

    @GetMapping("/portfolio/n")
    public String portfolioN(Model model) {
        return "stock/portfolio_n";
    }


    @GetMapping("/favorite/list")
    @ResponseBody
    public List<StockFavoriteDTO> favoriteList(@RequestParam(value = "location", required = false) String location) {
        return stockService.getAllStockFavoriteDTO(location);
    }


    @GetMapping("/portfolio/list")
    @ResponseBody
    public List<StockPortfolioDTO> portfolioList(@RequestParam(value = "location", required = false) String location) {
        return stockService.getAllStockPortfolioDTO(location);
    }


    @GetMapping("/detail")
    public String detail(Model model, @RequestParam(value = "ticker", required = false) String ticker) {
        model.addAttribute("details", stockService.findAllStockMetaDetailByTicker(ticker));
        return "stock/detail";
    }


    @DeleteMapping("/detail")
    @ResponseBody
    public void detail(String id) {
        stockService.deleteStockMetaDetailById(id);
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