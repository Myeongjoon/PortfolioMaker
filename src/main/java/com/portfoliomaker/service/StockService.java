package com.portfoliomaker.service;

import com.portfoliomaker.dto.stock.StockPortfolioDTO;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.stock.StockPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    @Autowired
    StockPortfolioRepository stockPortfolioRepository;

    public List<StockPortfolioDTO> getAllStockPortfolioDTO() {
        List<StockPortfolioDTO> response = new ArrayList<>();
        for (StockPortfolio p : stockPortfolioRepository.findAll()) {
            response.add(new StockPortfolioDTO(p));
        }
        return response;
    }

    public void delete(String ticker) {
        stockPortfolioRepository.deleteById(ticker);
    }

    public void save(List<StockPortfolio> stockPortfolios) {
        this.stockPortfolioRepository.saveAll(stockPortfolios);
    }

    public void save(String ticker, int count) {
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.ticker = ticker;
        stockPortfolio.count = count;
        stockPortfolioRepository.save(stockPortfolio);
    }
}
