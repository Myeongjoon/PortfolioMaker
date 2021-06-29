package com.portfoliomaker.service;

import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository portfolioRepository;

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
}
