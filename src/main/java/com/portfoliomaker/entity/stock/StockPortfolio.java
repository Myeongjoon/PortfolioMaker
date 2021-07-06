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

    /**
     * 구매 가격 합산
     */
    public Long buyPriceSum;

    /**
     * 예 : 주식, 펀드
     */
    public String type;

    /**
     * 현재 가격 합산
     */
    public Long currentPriceSum;

    /**
     * 주식 위치
     */
    public String location;

    /**
     * 전일 대비 상승량
     */
    public Double previousRate;
}
