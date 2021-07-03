package com.portfoliomaker.main.p2p;

import com.portfoliomaker.main.Application;
import com.portfoliomaker.main.TestUtil;
import com.portfoliomaker.service.p2p.TogetherFundingParsingService;
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
        String source = TestUtil.getHtmlByString("p2p/together.html");
        Document document = Jsoup.parse(source);
        assertEquals(togetherFundingParsingService.parseTogether(document).price, 2612545);
    }
}
