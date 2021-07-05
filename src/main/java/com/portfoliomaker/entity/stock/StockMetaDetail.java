package com.portfoliomaker.entity.stock;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class StockMetaDetail {
    @Id
    public String id;
    /**
     * 주식 ticker
     */
    public String ticker;

    /**
     * 메타 이름
     */
    public String name;

    /**
     * 가격 크롤링 기준일
     */
    public Date priceDate;

    /**
     * 기준 가격
     */
    public Long price;

    public String hash() {
        return this.ticker;
    }

    public StockMetaDetail(StockMeta p) {
        this.id = p.hash();
        this.ticker = p.ticker;
        this.name = p.name;
        this.priceDate = p.priceDate;
        this.price = p.price;
    }

    public StockMetaDetail() {

    }
}
