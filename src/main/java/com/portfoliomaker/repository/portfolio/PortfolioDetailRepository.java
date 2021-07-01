package com.portfoliomaker.repository.portfolio;

import com.portfoliomaker.entity.portfolio.PortfolioDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioDetailRepository extends JpaRepository<PortfolioDetail, String> {


    List<PortfolioDetail> findByNameOrderByDateDesc(String name);
}
