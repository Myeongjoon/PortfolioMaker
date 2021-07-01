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
public class MRParsingService {
    public ArrayList<StockPortfolio> parse(Document document) {
        ArrayList<StockPortfolio> response = new ArrayList<>();
        Elements codes = document.select("#stockTable2").select("tbody").select("tr");
        for (Element e : codes) {
            Element codeElement = e.selectFirst(".l");
            Element countElement = e.select(".r").get(1);
            Element buyPriceSumElement = e.select(".r").get(2);
            Element currentPriceSumElement = e.select(".r").get(3);
            StockPortfolio temp = new StockPortfolio();
            String target = codeElement.toString();
            String code = target.split("&quot;")[1];
            long count = StringUtil.parseMoney(countElement.text());
            long buyPriceSum = StringUtil.parseMoney(buyPriceSumElement.text());
            long currentPriceSum = StringUtil.parseMoney(currentPriceSumElement.text());
            temp.ticker = code;
            temp.count = count;
            temp.type = TypeConst.STOCK;
            temp.buyPriceSum = buyPriceSum;
            temp.currentPriceSum = currentPriceSum;
            response.add(temp);
        }
        return response;
    }
}
