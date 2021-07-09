package com.portfoliomaker.main.stock;

import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.NaverParsingService;
import com.portfoliomaker.service.YahooParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class YahooParsingServiceTest {
    @Autowired
    YahooParsingService yahooParsingService;

    @Test
    public void naverFinanceTest() {
        String source = TestUtil.getHtmlByString("yahoo/yahooFinance.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPrice response = yahooParsingService.parse(document, "013360");
        assertEquals(response.currentPrice, 139.96);
        assertEquals(response.previousRate, 1.96);
        //assertEquals(response.previousRate, 21.12);
        //assertEquals(response.name, "일성건설");
        //assertEquals(response.ticker, "013360");
        //assertEquals(response.location, "KOSPI");
    }
}
