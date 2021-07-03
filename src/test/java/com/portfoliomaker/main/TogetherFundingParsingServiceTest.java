package com.portfoliomaker.main;

import com.portfoliomaker.service.TogetherFundingParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class TogetherFundingParsingServiceTest {
    @Autowired
    TogetherFundingParsingService togetherFundingParsingService;

    @Test
    public void fundTableTest() {
        String source = TestUtil.getHtmlByString("together.html");
        Document document = Jsoup.parse(source);
        assertEquals(togetherFundingParsingService.parseTogether(document).currentPriceSum, 2612545);
    }
}
