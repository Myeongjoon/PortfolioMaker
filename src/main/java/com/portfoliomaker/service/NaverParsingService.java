package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NaverParsingService {

    public StockPrice parse(Document document,String ticker) {
        StockPrice stockPrice = new StockPrice();
        Element codes = document.select("#chart_area").select(".today").select("p").first();
        String name = document.select(".wrap_company").select("a").first().text();
        String loc = document.select(".description").first().select("img").attr("alt");
        document.select("#time").select(".date").select("span").remove();
        String time = document.select("#time").select(".date").text();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date();
        try {
            date = transFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long price = StringUtil.parseMoney(codes.text());
        stockPrice.date = date;
        stockPrice.currentPrice = price;
        stockPrice.name = name;
        stockPrice.location = loc;
        stockPrice.ticker = ticker;
        stockPrice.id = stockPrice.hash();
        return stockPrice;
    }
}
