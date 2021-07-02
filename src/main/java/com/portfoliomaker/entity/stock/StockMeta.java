package com.portfoliomaker.entity.stock;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class StockMeta {
    @Id
    public String id;
    /**
     * 주식 ticker
     */
    public String ticker;

    public String hash() {
        return this.ticker;
    }
}
