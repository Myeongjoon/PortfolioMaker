package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class NaverParsingService {

    public StockPrice parse(Document document) {
        StockPrice stockPrice = new StockPrice();
        Element codes = document.select("#chart_area").select(".today").select("p").first();
        String name = document.select(".wrap_company").select("a").first().text();
        String loc = document.select(".description").first().select("img").attr("alt");
        System.out.println(codes.text().toString());
        long price = StringUtil.parseMoney(codes.text());
        stockPrice.currentPrice = price;
        stockPrice.name = name;
        stockPrice.location = loc;
        return stockPrice;
    }
}
