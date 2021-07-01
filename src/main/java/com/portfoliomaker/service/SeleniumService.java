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
    Logger logger = LoggerFactory.getLogger(SeleniumService.class);
    WebDriverWait wait;
    WebDriver driver;
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH_WINDOW = "src/main/resources/driver/91/window/chromedriver.exe";

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriverWait getWait(){
        return this.wait;
    }

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
}
