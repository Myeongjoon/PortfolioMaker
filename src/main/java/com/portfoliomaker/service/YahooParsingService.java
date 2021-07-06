package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockMeta;
import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class YahooParsingService {
    Logger logger = LoggerFactory.getLogger(YahooParsingService.class);
    @Autowired
    SeleniumService seleniumService;

    public StockMeta parseMeta(StockMeta meta) {
        String url = "https://finance.yahoo.com/quote/" + meta.ticker + "?p=" + meta.ticker;
        seleniumService.getDriver().get(url);
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source);
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("quote-header-info")));
        StockPrice stockPrice = parse(document, meta.ticker);
        meta.name = stockPrice.name;
        meta.previousRate = stockPrice.previousRate;
        if (meta.priceDate == null || meta.priceDate.before(stockPrice.date)) {
            meta.priceDate = stockPrice.date;
            meta.price = stockPrice.currentPrice;
        }
        return meta;
    }

    public StockMeta parse(StockMeta meta) {
        String url = "https://finance.naver.com/item/main.nhn?code=" + meta.ticker;
        seleniumService.getDriver().get(url);
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source);
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("chart_area")));
        StockPrice stockPrice = parse(document, meta.ticker);
        meta.name = stockPrice.name;
        meta.previousRate = stockPrice.previousRate;
        if (meta.priceDate == null || meta.priceDate.before(stockPrice.date)) {
            meta.priceDate = stockPrice.date;
            meta.price = stockPrice.currentPrice;
        }
        return meta;
    }

    /**
     * 야후 증권 크롤링
     *
     * @param document
     * @param ticker
     * @return
     */
    public StockPrice parse(Document document, String ticker) {
        StockPrice stockPrice = new StockPrice();
        Element quoteHeader = document.select("#quote-header-info").first();
        Elements spans = quoteHeader.select("span");
        Element target = spans.get(3);
        stockPrice.currentPrice = StringUtil.parseDoubleMoney(target.text());
        stockPrice.date = new Date();
        // TODO
        return stockPrice;
    }
}
