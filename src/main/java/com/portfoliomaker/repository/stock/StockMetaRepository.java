package com.portfoliomaker.repository.stock;

import com.portfoliomaker.entity.stock.StockMeta;
import com.portfoliomaker.entity.stock.StockPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMetaRepository extends JpaRepository<StockMeta, String> {

}
