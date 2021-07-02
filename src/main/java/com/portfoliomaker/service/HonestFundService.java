package com.portfoliomaker.service;

import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.portfolio.PortfolioRepository;
import com.portfoliomaker.util.StringUtil;
import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 어니스트 펀드 크롤러
 */
@Service
public class HonestFundService {
    @Autowired
    PortfolioService portfolioService;

    public void doProcess(WebDriver driver, WebDriverWait webDriverWait, String id, String password, String name) {
        login(driver, webDriverWait, id, password);
        driver.get("https://www.honestfund.kr/mypage/investor/investments");
        String source = driver.getPageSource();
        Document document = Jsoup.parse(source);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("investment-info-ul")));
        Portfolio p = parse(document, "김명준");
        portfolioService.save(p.name, p.price);
    }

    public void login(WebDriver driver, WebDriverWait webDriverWait, String id, String password) {
        driver.get("https://www.honestfund.kr/login");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        driver.findElement(By.name("email")).sendKeys(id);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("auth_login")).click();
        Util.sleep(1000);
    }

    public Portfolio parse(Document document, String name) {
        Portfolio portfolio = new Portfolio();
        String ui = document.select(".investment-info-ul").first().select("li").select("p").get(2).text();
        long remain = StringUtil.parseMoney(ui);
        portfolio.price = remain;
        portfolio.name = "어니스트펀드-" + name;
        return portfolio;
    }
}
