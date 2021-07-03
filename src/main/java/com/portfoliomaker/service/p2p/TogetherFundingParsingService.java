package com.portfoliomaker.service.p2p;

import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.service.PortfolioService;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TogetherFundingParsingService {
    Logger logger = LoggerFactory.getLogger(TogetherFundingParsingService.class);
    @Autowired
    PortfolioService portfolioService;

    public void doProcess(WebDriver webDriver, WebDriverWait webDriverWait, String id, String password, String name) {
        webDriver.get("https://www.together.co.kr/menu/index.php?menu=invest_history");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_id")));
        webDriver.findElement(By.id("user_id")).sendKeys(id);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.className("btn_darkblue")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("invest_state_area1")));
        String source = webDriver.getPageSource();
        Document document = Jsoup.parse(source);
        portfolioService.save("투게더-투자" + name, Math.toIntExact(parseTogether(document).price));
        portfolioService.save("투게더-예치금" + name, Math.toIntExact(parseTogetherDeposit(document).currentPriceSum));
    }

    public Portfolio parseTogether(Document document) {
        Portfolio response = new Portfolio();
        Elements tables = document.select("table").select(".w100").select(".invest_repaying_amount");
        response.price = StringUtil.parseMoney(tables.first().text());
        return response;
    }

    public StockPortfolio parseTogetherDeposit(Document document) {
        StockPortfolio response = new StockPortfolio();
        Elements tables = document.select(".left_money");
        response.currentPriceSum = StringUtil.parseMoney(tables.first().text());
        return response;
    }
}
