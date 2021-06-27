package com.portfoliomaker.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class SeleniumService {
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "./src/main/resources/driver/91/chromedriver";

    public void doProcess() {

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.google.com");
    }
}
