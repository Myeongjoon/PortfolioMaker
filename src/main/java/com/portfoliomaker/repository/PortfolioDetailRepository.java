package com.portfoliomaker.repository;

import com.portfoliomaker.entity.PortfolioDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioDetailRepository extends JpaRepository<PortfolioDetail, String> {


    List<PortfolioDetail> findByNameOrderByDateDesc(String name);
}
