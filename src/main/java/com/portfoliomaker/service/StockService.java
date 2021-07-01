package com.portfoliomaker.service;

import com.portfoliomaker.dto.stock.StockPortfolioDTO;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.stock.StockPortfolioRepository;
import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    StockPortfolioRepository stockPortfolioRepository;
    @Autowired
    SeleniumService seleniumService;
    @Autowired
    MRParsingService mrParsingService;
    @Autowired
    PortfolioService portfolioService;

    public List<StockPortfolioDTO> getAllStockPortfolioDTO() {
        List<StockPortfolioDTO> response = new ArrayList<>();
        for (StockPortfolio p : stockPortfolioRepository.findAll()) {
            response.add(new StockPortfolioDTO(p));
        }
        return response;
    }

    public void delete(String ticker) {
        stockPortfolioRepository.deleteById(ticker);
    }

    public void save(List<StockPortfolio> stockPortfolios) {
        this.stockPortfolioRepository.saveAll(stockPortfolios);
    }

    public void save(String ticker, int count) {
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.ticker = ticker;
        stockPortfolio.count = count;
        stockPortfolioRepository.save(stockPortfolio);
    }

    public void sync() {
        seleniumService.setDriver();
        login();
        //My자산
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("openHp('/hkd/hkd1001/r01.do', true);");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        //상품별 자산
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("javascript:openHp('/hkd/hkd1003/r01.do', false)");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        //주식 탭
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("javascript:move('03')");
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source, "ecu-kr");
        /*원화환산표시 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("exchangeWon")));
        seleniumService.getDriver().findElement(By.id("exchangeWon")).click();
        /*종목명 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("excelTable"), By.className("l")));
        /*기달리고 다시 가져오기*/
        source = seleniumService.getDriver().getPageSource();
        seleniumService.getDriver().close();
        document = Jsoup.parse(source);
        Element element = document.select("#excelTable").first();
        /*투자 내역 파싱*/
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        long sum = 0;
        for (StockPortfolio s : response) {
            sum += s.currentPriceSum;
        }
        save(response);
        portfolioService.save("주식", sum);
    }

    /**
     * 미래에셋대우 로그인
     */
    private void login() {
        seleniumService.getDriver().get("https://securities.miraeasset.com/login/form.do");
        logger.info("form");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        Util.sleep(1000);
        seleniumService.getDriver().findElement(By.name("usid")).sendKeys("joon8409");
        logger.info("send id");
        Util.sleep(3500);
        logger.info("password");
        //로그인 버튼 클릭
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("doSubmit();");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
    }
}
