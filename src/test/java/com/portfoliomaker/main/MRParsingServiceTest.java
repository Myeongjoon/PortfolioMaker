package com.portfoliomaker.main;

import com.portfoliomaker.dto.MR.MyPortfolioDTO;
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
        String source = TestUtil.getHtmlByString("MR/excelTable.html");
        Document document = Jsoup.parse(source);
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        assertEquals(response.size(), 17);
        assertEquals(response.get(0).ticker, "000080");
        assertEquals(response.get(0).count, 40);
        assertEquals(response.get(0).buyPriceSum, 1477500);
        assertEquals(response.get(0).currentPriceSum, 1474000);
    }

    @Test
    public void fundTableTest() {
        String source = TestUtil.getHtmlByString("MR/fundTable.html");
        Document document = Jsoup.parse(source);
        assertEquals(mrParsingService.parseFundSummary(document), 3870089);
    }

    @Test
    public void rpTableTest() {
        String source = TestUtil.getHtmlByString("rpTable.html");
        Document document = Jsoup.parse(source);
        assertEquals(mrParsingService.parseRPSummary(document), 42146747);
    }

    @Test
    public void MyPortfolioTest() {
        String source = TestUtil.getHtmlByString("MR/MyPortfolio.html");
        Document document = Jsoup.parse(source);
        ArrayList<MyPortfolioDTO> response = mrParsingService.parseMyPortfolio(document);
        assertEquals(response.size(), 6);
        assertEquals(response.get(3).name, "미래에셋-CMA/RP");
        assertEquals(response.get(3).currentPrice, 39764766);
    }
}
