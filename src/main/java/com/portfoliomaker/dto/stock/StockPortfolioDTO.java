package com.portfoliomaker.dto.stock;

import com.portfoliomaker.entity.stock.StockPortfolio;

import javax.persistence.*;
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
     * 크롤링된 현재 가격 합산
     */
    public String crawledPriceSum;

    /**
     * 전일 대비 가격
     */
    public String previousRate;

    /**
     * 수익률
     */
    public String rate;

    /**
     * 주식 섹터
     */
    public String sector;

    public String price;

    public StockPortfolioDTO(StockPortfolio p) {
        this.ticker = p.ticker;
        this.count = NumberFormat.getNumberInstance(Locale.US).format(p.count);
        this.buyPriceSum = NumberFormat.getNumberInstance(Locale.US).format(p.buyPriceSum);
        this.currentPriceSum = NumberFormat.getNumberInstance(Locale.US).format(p.currentPriceSum);
        this.previousRate = p.previousRate == null ? "" : NumberFormat.getNumberInstance(Locale.US).format(p.previousRate);
        this.rate = NumberFormat.getNumberInstance(Locale.US).format(((double) (p.currentPriceSum - p.buyPriceSum) / p.buyPriceSum) * 100);
    }

    public StockPortfolioDTO(Double currentPriceSum, Double buyPriceSum, String ticker, String name, String count, String previousRate, String sector) {
        this.ticker = ticker;
        this.name = name;
        this.count = count;
        this.buyPriceSum = NumberFormat.getNumberInstance(Locale.US).format(buyPriceSum);
        //TODO 환율 곱하도록 추가
        this.currentPriceSum = NumberFormat.getNumberInstance(Locale.US).format(currentPriceSum);
        this.previousRate = previousRate;
        this.rate = NumberFormat.getNumberInstance(Locale.US).format(((currentPriceSum - buyPriceSum) / buyPriceSum) * 100);
        this.sector = sector;
    }
}
