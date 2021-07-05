package com.portfoliomaker.repository.stock;

import com.portfoliomaker.entity.stock.StockMetaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMetaDetailRepository extends JpaRepository<StockMetaDetail, String> {

}
