package com.portfoliomaker.repository.stock;

import com.portfoliomaker.entity.stock.StockMetaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMetaDetailRepository extends JpaRepository<StockMetaDetail, String> {
    List<StockMetaDetail> findByTicker(String ticker);
    List<StockMetaDetail> findByTickerOrderByPriceDateDesc(String ticker);
}
