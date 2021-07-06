package com.portfoliomaker.repository.stock;

import com.portfoliomaker.entity.stock.StockMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMetaRepository extends JpaRepository<StockMeta, String> {
    List<StockMeta> findByTicker(String ticker);

    List<StockMeta> findByLocation(String location);
}
