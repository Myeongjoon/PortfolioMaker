package com.portfoliomaker.entity.stock;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class StockPrice {
    @Id
    public String id;
    /**
     * 주식 id
     */
    public String ticker;

    /**
     *
     */
    public Date date;

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
    public Double currentPrice;

    /**
     * 전일 대비 rate
     */
    public Double previousRate;

    /**
     * 예 : 주식, 펀드
     */
    public String type;

    public String hash() {
        return this.ticker + "" + date.toString();
    }
}
