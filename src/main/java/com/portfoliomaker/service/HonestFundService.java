package com.portfoliomaker.service;

import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 어니스트 펀드 크롤러
 */
@Service
public class HonestFundService {
    public void doProcess(WebDriver driver, WebDriverWait webDriverWait, String id, String password, String name) {
        login(driver, webDriverWait, id, password);
        driver.get("https://www.honestfund.kr/mypage/investor/investments");
    }

    public void login(WebDriver driver, WebDriverWait webDriverWait, String id, String password) {
        driver.get("https://www.honestfund.kr/login");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        driver.findElement(By.name("email")).sendKeys(id);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("auth_login")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("1")));
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
