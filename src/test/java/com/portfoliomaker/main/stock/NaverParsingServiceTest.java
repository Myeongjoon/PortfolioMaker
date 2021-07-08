package com.portfoliomaker.main.stock;

import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.NaverParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class NaverParsingServiceTest {
    @Autowired
    NaverParsingService naverParsingService;

    @Test
    public void naverFinanceTest() {
        String source = TestUtil.getHtmlByString("naver/naverFinance.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPrice response = naverParsingService.parse(document, "013360");
        assertEquals(response.currentPrice, 4415);
        assertEquals(response.previousRate, 21.12);
        assertEquals(response.name, "일성건설");
        assertEquals(response.ticker, "013360");
        assertEquals(response.location, "코스피");
    }

    @Test
    public void naverFinanceTest2() {
        String source = TestUtil.getHtmlByString("naver/naverFinance2.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPrice response = naverParsingService.parse(document, "019170");
        assertEquals(response.currentPrice, 69800);
        assertEquals(response.previousRate, -26.99);
        assertEquals(response.name, "신풍제약");
        assertEquals(response.ticker, "019170");
        assertEquals(response.location, "코스피");
    }

    @Test
    public void naverFinanceTest3() {
        String source = TestUtil.getHtmlByString("naver/naverFinance3.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPrice response = naverParsingService.parse(document, "019170");
        assertEquals(response.currentPrice, 82800);
    }

    @Test
    public void naver_finance_after_market_test() {
        String source = TestUtil.getHtmlByString("naver/naver_finance_after_market.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPrice response = naverParsingService.parse(document, "019170");
    }
}
