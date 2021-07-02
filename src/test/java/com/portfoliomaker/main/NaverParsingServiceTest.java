package com.portfoliomaker.main;

import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.service.MRParsingService;
import com.portfoliomaker.service.NaverParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class NaverParsingServiceTest {
    @Autowired
    NaverParsingService naverParsingService;

    @Test
    public void rpTableTest() {
        String source = TestUtil.getHtmlByString("naverFinance.html");
        Document document = Jsoup.parse(source, "utf-8");
        StockPortfolio response = naverParsingService.parse(document);
        assertEquals(response.currentPrice, 4415);
    }
}
