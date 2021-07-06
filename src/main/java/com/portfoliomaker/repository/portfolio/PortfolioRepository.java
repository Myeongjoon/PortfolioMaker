package com.portfoliomaker.repository.portfolio;

import com.portfoliomaker.e.PortfolioTypeEnum;
import com.portfoliomaker.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    List<Portfolio> findByType(PortfolioTypeEnum type);
}
