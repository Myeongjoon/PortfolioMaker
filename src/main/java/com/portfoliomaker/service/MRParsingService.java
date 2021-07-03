package com.portfoliomaker.service;

import com.portfoliomaker.dto.MR.MyPortfolioDTO;
import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MRParsingService {
    Logger logger = LoggerFactory.getLogger(MRParsingService.class);

    public long parseRPSummary(Document document) {
        Elements fundTable = document.select("#rpTable1");
        Element target = fundTable.select("td").get(1);
        String txt = target.text();
        String split = txt.split(" ")[0];
        return StringUtil.parseMoney(split);
    }

    public long parseFundSummary(Document document) {
        Elements fundTable = document.select("#fundTable");
        Element target = fundTable.select("td").get(1);
        String txt = target.text();
        String split = txt.split(" ")[0];
        return StringUtil.parseMoney(split);
    }

    public ArrayList<MyPortfolioDTO> parseMyPortfolio(Document document) {
        ArrayList<MyPortfolioDTO> response = new ArrayList<>();
        Element contentE = document.select("#contents").first();
        Element dep02Sec = contentE.select(".dep02Sec").get(1);
        Element body = dep02Sec.select("#hkd1001a01Tbody").first();
        Elements tr = body.select("tr");
        for (Element e : tr) {
            MyPortfolioDTO temp = new MyPortfolioDTO();
            String currentPriceString = e.select(".r").get(1).text();
            temp.name = e.select(".l").select("a").get(0).text();
            temp.currentPrice = StringUtil.parseMoney(currentPriceString);
            response.add(temp);
        }
        return response;
    }

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
            String ticker = target.split("&quot;")[1];
            long count = StringUtil.parseMoney(countElement.text());
            long buyPriceSum = StringUtil.parseMoney(buyPriceSumElement.text());
            long currentPriceSum = StringUtil.parseMoney(currentPriceSumElement.text());
            temp.location = "NASDAQ";
            //ticker가 A + 숫자일 경우 A 제거
            if (ticker.charAt(0) == 'A') {
                try {
                    long remain = Long.parseLong(ticker.substring(1));
                    ticker = ticker.substring(1);
                    temp.location = "코스피";
                } catch (Exception ex) {
                    temp.location = "NASDAQ";
                    //
                }
            }
            temp.ticker = ticker;
            temp.count = count;
            temp.type = TypeConst.STOCK;
            temp.buyPriceSum = buyPriceSum;
            temp.currentPriceSum = currentPriceSum;
            response.add(temp);
        }
        return response;
    }
}
