package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.stock.StockPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    StockPortfolioRepository stockPortfolioRepository;

    public List<StockPortfolio> findAll() {
        return stockPortfolioRepository.findAll();
    }
}
