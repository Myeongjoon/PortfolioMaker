package com.portfoliomaker.service;

import com.portfoliomaker.dto.stock.StockPortfolioDTO;
import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.stock.StockMeta;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.stock.StockMetaRepository;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    StockPortfolioRepository stockPortfolioRepository;
    @Autowired
    StockMetaRepository stockMetaRepository;
    @Autowired
    HonestFundService honestFundService;
    @Autowired
    SeleniumService seleniumService;
    @Autowired
    MRParsingService mrParsingService;
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    NaverParsingService naverParsingService;

    public List<StockPortfolioDTO> getAllStockPortfolioDTO() {
        List<StockPortfolioDTO> response = new ArrayList<>();
        for (StockPortfolio p : stockPortfolioRepository.findAll()) {
            List<StockMeta> stockMeta = stockMetaRepository.findByTicker(p.ticker);
            StockPortfolioDTO dto = new StockPortfolioDTO(p);
            if (stockMeta.size() != 0) {
                dto.name = stockMeta.get(0).name;
            }
            response.add(dto);
        }
        return response;
    }

    public List<StockMeta> getAllMetas() {
        return stockMetaRepository.findAll();
    }

    public void deleteMeta(String id) {
        stockMetaRepository.deleteById(id);
    }

    public void delete(String ticker) {
        stockPortfolioRepository.deleteById(ticker);
    }

    public void save(List<StockPortfolio> stockPortfolios) {
        this.stockPortfolioRepository.saveAll(stockPortfolios);
    }

    @Transactional
    public void deleteByType(String type) {
        for (StockPortfolio stockPortfolio : stockPortfolioRepository.findByType(type)) {
            this.stockPortfolioRepository.deleteById(stockPortfolio.ticker);
        }
    }

    public void save(String ticker, int count) {
        StockPortfolio stockPortfolio = new StockPortfolio();
        stockPortfolio.ticker = ticker;
        stockPortfolio.count = count;
        stockPortfolioRepository.save(stockPortfolio);
    }

    /**
     * p2p 크롤링
     */
    public void p2pSync() {
        try {
            seleniumService.setDriver();
            honestFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), "kimmj8409@gmail.com", "apt3550!1", "김명준");
            honestFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), "kimmj8409@gmail.com", "apt3550!1", "김명준");
            honestFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), "kimmj8409@gmail.com", "apt3550!1", "김명준");
            honestFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), "kimmj8409@gmail.com", "apt3550!1", "김명준");

        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * 주식 가격 크롤링
     */
    public void stockSync() {
        try {
            seleniumService.setDriver();
            parseNaverStock();
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * 네이버 증권 크롤링
     */
    public void parseNaverStock() {
        List<StockMeta> list = stockMetaRepository.findAll();
        for (StockMeta stockMeta : list) {
            if ((stockMeta.location != null && stockMeta.location.equals("코스피")) && (stockMeta.name == null || stockMeta.name.equals(""))) {
                naverParsingService.parseMeta(stockMeta);
                stockMetaRepository.save(stockMeta);
            }
        }
    }

    /**
     * 증권사 내 포트폴리오 sync
     */
    public void portfolioSync() {
        try {
            seleniumService.setDriver();
            login();
            parseStockPortfolio();
            parseFundPortfolio();
            parseCMAPortfolio();
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
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

    /**
     * 현재 주식 보유량 크롤링
     */
    private void parseStockPortfolio() {
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
        document = Jsoup.parse(source);
        Element element = document.select("#excelTable").first();
        /*투자 내역 파싱*/
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        long sum = 0;
        for (StockPortfolio s : response) {
            List<StockMeta> metas = stockMetaRepository.findByTicker(s.ticker);
            if (metas.size() == 0) {
                StockMeta stockMeta = new StockMeta();
                stockMeta.ticker = s.ticker;
                stockMeta.id = stockMeta.hash();
                stockMeta.location = s.location;
                stockMetaRepository.save(stockMeta);
            }
            sum += s.currentPriceSum;
        }
        /*이전 내역 삭제*/
        deleteByType(TypeConst.STOCK);
        save(response);
        portfolioService.save("주식", sum);
    }

    /**
     * 현재 펀드 보유량 크롤링
     */
    private void parseFundPortfolio() {
        //펀드 탭
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("javascript:move('02')");
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source, "ecu-kr");
        /*종목명 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("fundTable2"), By.className("l")));
        /*기달리고 다시 가져오기*/
        source = seleniumService.getDriver().getPageSource();
        document = Jsoup.parse(source);
        Element element = document.select("#fundTable").first();
        /*펀드 총액 파싱*/
        long response = mrParsingService.parseFundSummary(document);
        portfolioService.save("펀드", response);
    }

    /**
     * 현재 CMA 보유량 크롤링
     */
    private void parseCMAPortfolio() {
        //펀드 탭
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("javascript:move('04')");
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source, "ecu-kr");
        /*종목명 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("rpTable2"), By.className("l")));
        /*기달리고 다시 가져오기*/
        source = seleniumService.getDriver().getPageSource();
        document = Jsoup.parse(source);
        Element element = document.select("#rpTable").first();
        /*펀드 총액 파싱*/
        long response = mrParsingService.parseRPSummary(document);
        portfolioService.save("CMA", response);
    }
}
