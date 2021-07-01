package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockPortfolio;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MRParsingService {
    public ArrayList<StockPortfolio> parse(Document document) {
        ArrayList<StockPortfolio> response = new ArrayList<>();
        Elements codes = document.select("#stockTable2").select("tbody").select(".l");
        for (Element e : codes) {
            StockPortfolio temp = new StockPortfolio();
            String target = e.toString();
            String code = target.split("&quot;")[1];
            temp.ticker = code;
            response.add(temp);
        }
        return response;
    }
}
