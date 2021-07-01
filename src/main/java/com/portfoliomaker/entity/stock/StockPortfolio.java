package com.portfoliomaker.entity.stock;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockPortfolio {
    /**
     * 주식 id
     */
    @Id
    public String ticker;

    /**
     * 보유 잔고
     */
    public long count;
}
