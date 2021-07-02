package com.portfoliomaker.service;

import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NaverParsingService {

    public StockPortfolio parse(Document document) {
        StockPortfolio response = new StockPortfolio();
        Element codes = document.select("#chart_area").select(".today").select("p").first();
        System.out.println(codes.text().toString());
        long price = StringUtil.parseMoney(codes.text());
        response.currentPrice = price;
        return response;
    }
}
