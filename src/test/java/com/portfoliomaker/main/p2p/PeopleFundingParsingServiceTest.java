package com.portfoliomaker.main.p2p;

import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.p2p.PeopleFundService;
import com.portfoliomaker.service.p2p.TogetherFundingParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class PeopleFundingParsingServiceTest {
    @Autowired
    PeopleFundService peopleFundService;

    @Test
    public void parseDepositTest() {
        String source = TestUtil.getHtmlByString("p2p/peopleFund.html");
        Document document = Jsoup.parse(source);
        assertEquals(peopleFundService.parseDeposit(document, "김명준").price, 5010);
    }

    @Test
    public void parseRemainTest() {
        String source = TestUtil.getHtmlByString("p2p/peopleFund.html");
        Document document = Jsoup.parse(source);
        assertEquals(peopleFundService.parseRemain(document, "김명준").price, 3567324);
    }
}
