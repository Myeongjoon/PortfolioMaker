package com.portfoliomaker.dto.stock;

import com.portfoliomaker.entity.stock.StockMetaDetail;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.util.StringUtil;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;


public class StockDetailDTO {
    /**
     * 주식 id
     */
    public String ticker;

    /**
     * 보유 잔고
     */
    public String count;

    /**
     * 현재 가격 합산
     */
    public String price;

    /**
     * 가격 크롤링 기준일
     */
    public Date priceDate;

    /**
     * 수익률
     */
    public String rate;

    public StockDetailDTO(StockMetaDetail p) {
        this.ticker = p.ticker;
        this.price = StringUtil.formatMoney(p.price);
        this.priceDate = p.priceDate;
    }

    public StockDetailDTO() {

    }
}
