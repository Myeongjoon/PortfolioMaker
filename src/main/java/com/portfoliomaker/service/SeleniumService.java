package com.portfoliomaker.service;

import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.lang.model.util.Elements;
import java.util.Locale;

@Service
public class SeleniumService {
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
        Util.sleep(500);
        driver.findElement(By.name("usid")).sendKeys("joon8409");
        logger.info("send id");
        Util.sleep(3000);
        logger.info("password");
        ((JavascriptExecutor)driver).executeScript("doSubmit();");
        Util.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("openHp('/hkd/hkd1001/r01.do', true);");
        Util.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("javascript:openHp('/hkd/hkd1003/r01.do', false)");
        Util.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("javascript:move('03')");
        String source = driver.getPageSource();
        Document document = Jsoup.parse(source,"ecu-kr");
        Element element = document.select("#excelTable").first();
        logger.info(element.toString());
        //driver.findElement(By.id("excelTable"))
    }
}
