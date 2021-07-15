package com.portfoliomaker.main.stock;

import com.portfoliomaker.entity.stock.StockPrice;
import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.NaverParsingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class NaverParsingServiceTest {
    @Autowired
    NaverParsingService naverParsingService;

    @Test
    public void naverFinanceTest() {
        String source = TestUtil.getHtmlByString("stock/naver/naverFinance.html");
        StockPrice response = naverParsingService.parse(source, "013360");
        assertEquals(response.currentPrice, 4415);
        assertEquals(response.previousRate, 21.12);
        assertEquals(response.name, "일성건설");
        assertEquals(response.ticker, "013360");
        assertEquals(response.location, "KOSPI");
    }

    @Test
    public void naverFinanceTest2() {
        String source = TestUtil.getHtmlByString("stock/naver/naverFinance2.html");
        StockPrice response = naverParsingService.parse(source, "019170");
        assertEquals(response.currentPrice, 69800);
        assertEquals(response.previousRate, -26.99);
        assertEquals(response.name, "신풍제약");
        assertEquals(response.ticker, "019170");
        assertEquals(response.location, "KOSPI");
    }

    @Test
    public void naverFinanceTest3() {
        String source = TestUtil.getHtmlByString("stock/naver/naverFinance3.html");
        StockPrice response = naverParsingService.parse(source, "019170");
        assertEquals(response.currentPrice, 82800);
    }

    @Test
    public void 개장전_테스트() throws ParseException {
        String source = TestUtil.getHtmlByString("stock/naver/naver_before_open.html");
        StockPrice response = naverParsingService.parse(source, "019170");
        assertEquals(response.location, "KOSPI");
        String targetDateString = "2021.07.15 00:00";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date targetDate = transFormat.parse(targetDateString);
        assertEquals(response.date, targetDate);
    }


    @Test
    public void naverFinanceTest5() {
        String source = TestUtil.getHtmlByString("stock/naver/naverFinance5.html");
        StockPrice response = naverParsingService.parse(source, "096770");
        assertEquals(response.previousRate, -0.18);
    }

    @Test
    public void naver_finance_after_market_test() {
        String source = TestUtil.getHtmlByString("stock/naver/naver_finance_after_market.html");
        StockPrice response = naverParsingService.parse(source, "019170");
    }
}
