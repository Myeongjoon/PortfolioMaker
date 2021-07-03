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

@Service
public class TogetherFundingParsingService {
    Logger logger = LoggerFactory.getLogger(TogetherFundingParsingService.class);

    public StockPortfolio parseTogether(Document document) {
        StockPortfolio response = new StockPortfolio();
        Elements tables = document.select("table").select(".w100").select(".invest_repaying_amount");
        response.currentPriceSum = StringUtil.parseMoney(tables.first().text());
        return response;
    }
}
