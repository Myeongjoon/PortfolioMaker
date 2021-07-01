package com.portfoliomaker.main;

import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.service.MRParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
public class MRParsingServiceTest {
    @Autowired
    MRParsingService mrParsingService;

    @Test
    public void excelTableTest() {
        String source = TestUtil.getHtmlByString("excelTable.txt");
        Document document = Jsoup.parse(source);
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        assertEquals(response.size(), 17);
        assertEquals(response.get(0).ticker, "A000080");
        assertEquals(response.get(0).count, 40);
        assertEquals(response.get(0).buyPriceSum, 1477500);
        assertEquals(response.get(0).currentPriceSum, 1474000);
    }
}
