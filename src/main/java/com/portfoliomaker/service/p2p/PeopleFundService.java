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
 * 피플 펀딩 크롤러
 */
@Service
public class PeopleFundService {
    private static final String NAME = "피플펀딩";
    @Autowired
    PortfolioService portfolioService;

    public void doProcess(WebDriver driver, WebDriverWait webDriverWait, String id, String password, String name) {
        driver.get("https://www.peoplefund.co.kr/mypage/invest/");
        login(driver, webDriverWait, id, password);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mypage-container")));
        //sleep 조건 찾기 귀찮아서 그냥 슬립
        Util.sleep(1000);
        String source = driver.getPageSource();
        Document document = Jsoup.parse(source);
        Portfolio p = parseRemain(document, name);
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
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-login")));
        driver.findElement(By.name("email")).sendKeys(id);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("btn-login")).click();
        Util.sleep(1000);
    }

    /**
     * 투자 금액 파싱
     *
     * @param document
     * @param name
     * @return
     */
    public Portfolio parseRemain(Document document, String name) {
        Portfolio portfolio = new Portfolio();
        Elements investReports = document.select(".invest-report");
        Elements secTotalAssets = investReports.select(".sec-total-assets");
        Elements dd = secTotalAssets.select(".fold-area").select("dd");
        String ui = dd.get(2).text();
        portfolio.price = StringUtil.parseMoney(ui);
        ui = dd.get(3).text();
        portfolio.price += StringUtil.parseMoney(ui);
        portfolio.name = NAME + "-투자-" + name;
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
        portfolio.name = NAME + "-예치금-" + name;
        return portfolio;
    }
}
