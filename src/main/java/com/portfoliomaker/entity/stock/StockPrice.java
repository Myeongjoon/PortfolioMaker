package com.portfoliomaker.entity.stock;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockPrice {
    /**
     * 주식 id
     */
    @Id
    public String ticker;

    /**
     * 주식 이름
     */
    public String name;

    /**
     * 예 : nasdaq
     */
    public String location;

    /**
     * 현재 단위 가격
     */
    public Long currentPrice;

    /**
     * 예 : 주식, 펀드
     */
    public String type;
}
