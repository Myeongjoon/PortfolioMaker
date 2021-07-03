package com.portfoliomaker.main.p2p;

import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.p2p.HonestFundService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class HonestFundTest {
    @Autowired
    HonestFundService honestFundService;

    @Test
    public void fundTableTest() {
        String source = TestUtil.getHtmlByString("p2p/honestfund.html");
        Document document = Jsoup.parse(source);
        Portfolio portfolio = honestFundService.parse(document, "김명준");
        assertEquals(portfolio.price, 2150180);
        assertEquals(portfolio.name, "어니스트펀드-김명준");
    }
}
