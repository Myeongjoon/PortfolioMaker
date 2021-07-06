package com.portfoliomaker.dto.stock;

import com.portfoliomaker.entity.stock.StockMeta;
import com.portfoliomaker.entity.stock.StockMetaDetail;
import com.portfoliomaker.util.StringUtil;

import java.util.Date;


public class StockFavoriteDTO {
    /**
     * 주식 id
     */
    public String ticker;

    /**
     * 현재 가격 합산
     */
    public String price;

    /**
     * 주식 이름
     */
    public String name;

    /**
     * 가격 크롤링 기준일
     */
    public Date priceDate;

    /**
     * 전일 대비 가격
     */
    public String previousRate;

    /**
     * 수익률
     */
    public String rate;

    public StockFavoriteDTO(StockMeta p) {
        this.ticker = p.ticker;
        this.name = p.name;
        this.price = StringUtil.formatMoney(p.price == null ? 0 : p.price);
        this.priceDate = p.priceDate;
    }

    public StockFavoriteDTO() {

    }
}
