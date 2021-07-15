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

    /**
     * 메타 이름
     */
    public String name;

    /*
    주식 위치
     */
    public String location;

    /**
     * 가격 크롤링 기준일
     */
    public Date priceDate;

    /**
     * 기준 가격
     */
    public Double price;

    /**
     * 전일 대비 상승량
     */
    public Double previousRate;

    /**
     * 주식 섹터
     */
    public String sector;

    public String hash() {
        return this.ticker + this.priceDate.toString();
    }
}
