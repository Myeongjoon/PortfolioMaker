package com.portfoliomaker.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class SeleniumService {
    Logger logger = LoggerFactory.getLogger(SeleniumService.class);
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "./src/main/resources/driver/91/chromedriver";

    public void setDriver() {
        switch (System.getProperty("os.name").toLowerCase(Locale.ROOT)) {
            case "windows 10":
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
                break;
            default:
                logger.info("unsupported type : " + System.getProperty("os.name"));
        }
    }

    public void doProcess() {
        this.setDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://securities.miraeasset.com/");
    }
}
