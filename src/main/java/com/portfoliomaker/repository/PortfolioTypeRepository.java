package com.portfoliomaker.repository;

import com.portfoliomaker.entity.PortfolioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioTypeRepository extends JpaRepository<PortfolioType, String> {

}
