package com.portfoliomaker.service;

import com.portfoliomaker.dto.PortfolioDTO;
import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.repository.PortfolioDetailRepository;
import com.portfoliomaker.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    PortfolioDetailRepository portfolioDetailRepository;

    public List<PortfolioDTO> getDetail() {
        List<Portfolio> data = portfolioRepository.findAll();
        List<PortfolioDTO> response = new ArrayList<>();
        long sum = 0;
        for (Portfolio p : data) {
            sum += p.price;
            PortfolioDTO dto = new PortfolioDTO(p);
            response.add(dto);
        }
        PortfolioDTO sum_port = new PortfolioDTO("합계", sum);
        response.add(sum_port);
        return response;
    }

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public void deleteById(String id) {
        portfolioRepository.deleteById(id);
    }
}
