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
public class NaverParsingService {
    Logger logger = LoggerFactory.getLogger(NaverParsingService.class);
    @Autowired
    SeleniumService seleniumService;

    public StockMeta parseMeta(StockMeta meta) {
        String url = "https://finance.naver.com/item/main.nhn?code=" + meta.ticker;
        seleniumService.getDriver().get(url);
        String source = seleniumService.getDriver().getPageSource();
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("chart_area")));
        StockPrice stockPrice = parse(source, meta.ticker);
        meta.name = stockPrice.name;
        meta.previousRate = stockPrice.previousRate;
        if (meta.priceDate == null || meta.priceDate.before(stockPrice.date)) {
            meta.priceDate = stockPrice.date;
            meta.price = stockPrice.currentPrice;
        }
        return meta;
    }


    /**
     * 네이버 증권 크롤링
     *
     * @param source : 원본 html
     * @param ticker : 주식 ticker
     * @return : 파싱된 정보
     */
    public StockPrice parse(String source, String ticker) {
        StockPrice stockPrice = new StockPrice();
        Document document = Jsoup.parse(source);
        Element codes = document.select("#chart_area").select(".today").select("p").first();
        String name = document.select(".wrap_company").select("a").first().text();
        Elements previousRate = document.select(".no_exday");
        previousRate.select(".sp_txt1").remove();
        //데이터 변경된 경우 블라인드라는 태그가 추가됨.
        previousRate.select(".blind").remove();
        String previousRateReplaced = previousRate.select("em").get(1).text().replace("%", "");
        try {
            stockPrice.previousRate = StringUtil.parseDoubleMoney(previousRateReplaced);
        } catch (NumberFormatException e) {
            logger.warn(source);
            throw e;
        }
        document.select("#time").select(".date").select("span").remove();
        String time = document.select("#time").select(".date").text();
        Date date = new Date();
        if (time.length() == 10) {
            try {
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
                date = transFormat.parse(time);
                date.setHours(15);
                //TODO 장 시작 이전 체크
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                date = transFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        double price = 0;
        try {
            price = StringUtil.parseDoubleMoney(codes.text());
        } catch (Exception e) {
            logger.warn(document.toString());
            throw e;
        }
        stockPrice.date = date;
        stockPrice.currentPrice = price;
        stockPrice.name = name;
        stockPrice.location = "KOSPI";
        stockPrice.ticker = ticker;
        stockPrice.id = stockPrice.hash();
        return stockPrice;
    }
}
