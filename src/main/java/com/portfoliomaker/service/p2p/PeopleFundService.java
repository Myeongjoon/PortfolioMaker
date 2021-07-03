package com.portfoliomaker.service.p2p;

import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.service.PortfolioService;
import com.portfoliomaker.util.StringUtil;
import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 어니스트 펀드 크롤러
 */
@Service
public class PeopleFundService {
    @Autowired
    PortfolioService portfolioService;

    public void doProcess(WebDriver driver, WebDriverWait webDriverWait, String id, String password, String name) {
        login(driver, webDriverWait, id, password);
        driver.get("https://www.honestfund.kr/mypage/investor/investments");
        String source = driver.getPageSource();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("investment-info-ul")));
        Document document = Jsoup.parse(source);
        Portfolio p = parse(document, name);
        portfolioService.save(p.name, p.price);

        Document document2 = Jsoup.parse(source);
        p = parseDeposit(document2, name);
        portfolioService.save(p.name, p.price);
    }

    /**
     * 로그인 진행
     *
     * @param driver
     * @param webDriverWait
     * @param id
     * @param password
     */
    public void login(WebDriver driver, WebDriverWait webDriverWait, String id, String password) {
        driver.get("https://www.honestfund.kr/login");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        driver.findElement(By.name("email")).sendKeys(id);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("auth_login")).click();
        Util.sleep(1000);
    }

    /**
     * 잔여 투자 내역
     *
     * @param document
     * @param name
     * @return
     */
    public Portfolio parse(Document document, String name) {
        Portfolio portfolio = new Portfolio();
        String ui = document.select(".investment-info-ul").first().select("li").select("p").get(2).text();
        long remain = StringUtil.parseMoney(ui);
        portfolio.price = remain;
        portfolio.name = "어니스트펀드-" + name;
        return portfolio;
    }

    /**
     * 예치금 파싱
     *
     * @param document
     * @param name
     * @return
     */
    public Portfolio parseDeposit(Document document, String name) {
        Portfolio portfolio = new Portfolio();
        Elements investReports = document.select(".invest-report");
        Elements secTotalAssets = investReports.select(".sec-total-assets");
        Elements dd = secTotalAssets.select(".fold-area").select("dd");
        String ui = dd.get(1).text();
        portfolio.price = StringUtil.parseMoney(ui);
        portfolio.name = "투게더펀딩-예치금-" + name;
        return portfolio;
    }
}
