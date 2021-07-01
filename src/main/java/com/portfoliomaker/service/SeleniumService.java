package com.portfoliomaker.service;

import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class SeleniumService {
    @Autowired
    StockService stockService;
    @Autowired
    MRParsingService mrParsingService;
    Logger logger = LoggerFactory.getLogger(SeleniumService.class);
    protected WebDriverWait wait;
    WebDriver driver;
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH_WINDOW = "src/main/resources/driver/91/window/chromedriver.exe";

    public void setDriver() {
        switch (System.getProperty("os.name").toLowerCase(Locale.ROOT)) {
            case "windows 10":
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH_WINDOW);
                break;
            default:
                logger.info("unsupported type : " + System.getProperty("os.name"));
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 1000);
    }

    public void doProcess() {
        this.setDriver();
        driver.get("https://securities.miraeasset.com/login/form.do");
        logger.info("form");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        Util.sleep(1000);
        driver.findElement(By.name("usid")).sendKeys("joon8409");
        logger.info("send id");
        Util.sleep(3500);
        logger.info("password");
        //로그인 버튼 클릭
        ((JavascriptExecutor) driver).executeScript("doSubmit();");
        Util.sleep(1500);
        //My자산
        ((JavascriptExecutor) driver).executeScript("openHp('/hkd/hkd1001/r01.do', true);");
        Util.sleep(1500);
        ((JavascriptExecutor) driver).executeScript("javascript:openHp('/hkd/hkd1003/r01.do', false)");
        Util.sleep(1500);
        //주식 탭
        ((JavascriptExecutor) driver).executeScript("javascript:move('03')");
        String source = driver.getPageSource();
        Document document = Jsoup.parse(source, "ecu-kr");
        /*원화환산표시 기달리기*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exchangeWon")));
        driver.findElement(By.id("exchangeWon")).click();
        /*종목명 기달리기*/
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("excelTable"), By.className("l")));
        /*기달리고 다시 가져오기*/
        source = driver.getPageSource();
        document = Jsoup.parse(source);
        Element element = document.select("#excelTable").first();
        /*투자 내역 파싱*/
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        stockService.save(response);
        driver.close();
    }
}
