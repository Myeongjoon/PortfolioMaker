package com.portfoliomaker.repository;

import com.portfoliomaker.entity.PortfolioDetail;
import com.portfoliomaker.entity.PortfolioDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioDetailRepository extends JpaRepository<PortfolioDetail, PortfolioDetailId> {
}
