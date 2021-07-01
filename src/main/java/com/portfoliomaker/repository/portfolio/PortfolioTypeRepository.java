package com.portfoliomaker.repository.portfolio;

import com.portfoliomaker.entity.portfolio.PortfolioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioTypeRepository extends JpaRepository<PortfolioType, String> {

}
