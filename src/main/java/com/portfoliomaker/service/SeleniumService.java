package com.portfoliomaker.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class SeleniumService {
    Logger logger = LoggerFactory.getLogger(SeleniumService.class);
    WebDriverWait wait;
    WebDriver driver;
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH_WINDOW = "src/main/resources/driver/91/window/chromedriver.exe";
    public static final String WEB_DRIVER_PATH_MAC = "src/main/resources/driver/91/mac/chromedriver";

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriverWait getWait() {
        return this.wait;
    }

    public void setDriver() {
        this.setDriver(false);
    }

    public void setDriver(boolean isHeadless) {
        switch (System.getProperty("os.name").toLowerCase(Locale.ROOT)) {
            case "windows 10":
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH_WINDOW);
                break;
            case "mac os x":
                System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH_MAC);
                break;
            default:
                logger.info("unsupported type : " + System.getProperty("os.name"));
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(isHeadless);
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 1000);
    }
}
