package com.portfoliomaker.dto.stock;

import com.portfoliomaker.entity.stock.StockPortfolio;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.NumberFormat;
import java.util.Locale;


public class StockPortfolioDTO {
    /**
     * 주식 id
     */
    public String ticker;
    /**
     * 주식 이름
     */
    public String name;

    /**
     * 보유 잔고
     */
    public String count;

    /**
     * 구매 가격 합산
     */
    public String buyPriceSum;

    /**
     * 현재 가격 합산
     */
    public String currentPriceSum;

    /**
     * 수익률
     */
    public String rate;

    public StockPortfolioDTO(StockPortfolio p) {
        this.ticker = p.ticker;
        this.count = NumberFormat.getNumberInstance(Locale.US).format(p.count);
        this.buyPriceSum = NumberFormat.getNumberInstance(Locale.US).format(p.buyPriceSum);
        this.currentPriceSum = NumberFormat.getNumberInstance(Locale.US).format(p.currentPriceSum);
        this.rate = NumberFormat.getNumberInstance(Locale.US).format(((double) (p.currentPriceSum - p.buyPriceSum) / p.buyPriceSum) * 100);
    }
}
