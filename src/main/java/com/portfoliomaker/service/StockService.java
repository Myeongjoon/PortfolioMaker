package com.portfoliomaker.service;

import com.portfoliomaker.dto.MR.MyPortfolioDTO;
import com.portfoliomaker.dto.stock.StockDetailDTO;
import com.portfoliomaker.dto.stock.StockFavoriteDTO;
import com.portfoliomaker.dto.stock.StockPortfolioDTO;
import com.portfoliomaker.e.PortfolioTypeEnum;
import com.portfoliomaker.e.TypeConst;
import com.portfoliomaker.entity.stock.StockMeta;
import com.portfoliomaker.entity.stock.StockMetaDetail;
import com.portfoliomaker.entity.stock.StockPortfolio;
import com.portfoliomaker.repository.stock.StockMetaDetailRepository;
import com.portfoliomaker.repository.stock.StockMetaRepository;
import com.portfoliomaker.repository.stock.StockPortfolioRepository;
import com.portfoliomaker.service.p2p.HonestFundService;
import com.portfoliomaker.service.p2p.PeopleFundService;
import com.portfoliomaker.service.p2p.TogetherFundingParsingService;
import com.portfoliomaker.util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    StockPortfolioRepository stockPortfolioRepository;
    @Autowired
    StockMetaRepository stockMetaRepository;
    @Autowired
    StockMetaDetailRepository stockMetaDetailRepository;
    @Autowired
    HonestFundService honestFundService;
    @Autowired
    PeopleFundService peopleFundService;
    @Autowired
    SeleniumService seleniumService;
    @Autowired
    MRParsingService mrParsingService;
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    NaverParsingService naverParsingService;
    @Autowired
    YahooParsingService yahooParsingService;
    @Autowired
    TogetherFundingParsingService togetherFundingParsingService;

    public void deleteStockMetaDetailById(String ticker) {
        stockMetaDetailRepository.deleteById(ticker);
    }

    public List<StockMetaDetail> findAllStockMetaDetailByTicker(String ticker) {
        return stockMetaDetailRepository.findByTickerOrderByPriceDateDesc(ticker);
    }

    public List<StockDetailDTO> findDetailList(String ticker) {
        List<StockDetailDTO> response = new ArrayList<>();
        List<StockMetaDetail> targets = stockMetaDetailRepository.findByTicker(ticker);
        for (StockMetaDetail detail : targets) {
            StockDetailDTO detailDTO = new StockDetailDTO(detail);
            response.add(detailDTO);
        }
        return response;
    }

    public static final String findAllStockPortfolioDTOQuery = """
            select
            p.ticker as ticker,
            p.buy_price_sum as buy_price_sum,
            p.count as count,
            case when l.price is null then p.current_price_sum else l.price * p.count end as current_price_sum,
            p.location as location,
            l.name as name,
            l.previous_rate as previous_rate,
            l.price as price
            from stock_portfolio p left join stock_meta l on p.ticker = l.ticker """;

    @PersistenceContext
    EntityManager em;

    /**
     * 내 보유 종목
     *
     * @param location
     * @return
     */
    public List<StockPortfolioDTO> getAllStockPortfolioDTO(String location) {
        String query = findAllStockPortfolioDTOQuery;
        if (location != null) {
            query += " where l.location = \"" + location + "\"";
        }
        Query nativeQuery = em.createNativeQuery(query, "StockPortfolioMapping");

        @SuppressWarnings("unchecked")
        List<StockPortfolioDTO> response = nativeQuery.getResultList();
        return response;
    }

    /**
     * 내 관심 종목
     *
     * @param location
     * @return
     */
    public List<StockFavoriteDTO> getAllStockFavoriteDTO(String location) {
        List<StockFavoriteDTO> response = new ArrayList<>();
        List<StockMeta> target;
        if (location == null) {
            target = stockMetaRepository.findAll();
        } else {
            target = stockMetaRepository.findByLocation(location);
        }
        for (StockMeta p : target) {
            StockFavoriteDTO dto = new StockFavoriteDTO(p);
            dto.previousRate = p.previousRate == null ? "" : NumberFormat.getNumberInstance(Locale.US).format(p.previousRate);
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
        honestFundSync("kimmj8409@gmail.com", "apt3550!1", "김명준");
        honestFundSync("rlaeodudslak@naver.com", "apt3550!1", "김대영");
        honestFundSync("kimdg691020@naver.com", "apt3550!1", "김동길");
        togetherFundSync("kimmj8409@gmail.com", "apt3550!1", "김명준");
        peopleFundSync("5252555@naver.com", "vlvmfaudwns!1", "김명준");
        logger.info("p2p sync finished");
    }

    /**
     * 투게더 펀딩 sync
     *
     * @param id
     * @param password
     * @param name
     */
    public void togetherFundSync(String id, String password, String name) {
        try {
            seleniumService.setDriver(true);
            togetherFundingParsingService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), id, password, name);
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * 피플 펀드 싱크
     *
     * @param id
     * @param password
     * @param name
     */
    public void peopleFundSync(String id, String password, String name) {
        try {
            seleniumService.setDriver(true);
            peopleFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), id, password, name);
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * 어니스트 펀드 싱크
     *
     * @param id
     * @param password
     * @param name
     */
    public void honestFundSync(String id, String password, String name) {
        try {
            seleniumService.setDriver(true);
            honestFundService.doProcess(seleniumService.getDriver(), seleniumService.getWait(), id, password, name);
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
    public void stockSync(String location) {
        try {
            seleniumService.setDriver(true);
            if (location == null || location.equals("") || location.equals("KOSPI")) {
                parseNaverStock();
            }
            if (location == null || location.equals("") || location.equals("NASDAQ")) {
                parseYahooStock();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * 야후 크롤링
     */
    public void parseYahooStock() {
        List<StockMeta> list = stockMetaRepository.findAll();
        for (StockMeta stockMeta : list) {
            if (stockMeta.location != null && stockMeta.location.equals("NASDAQ")) {
                yahooParsingService.parseMeta(stockMeta);
                staveStockMeta(stockMeta);
            }
        }
        logger.info("finish yahoo crawling");
    }

    /**
     * 네이버 증권 크롤링
     */
    public void parseNaverStock() {
        List<StockMeta> list = stockMetaRepository.findAll();
        for (StockMeta stockMeta : list) {
            if (stockMeta.location != null && stockMeta.location.equals("KOSPI")) {
                naverParsingService.parseMeta(stockMeta);
                staveStockMeta(stockMeta);
            }
        }
        logger.info("finish naver crawling");
    }

    public void staveStockMeta(StockMeta stockMeta) {
        stockMetaRepository.save(stockMeta);
        Optional<StockMetaDetail> detail = stockMetaDetailRepository.findById(stockMeta.id);
        if (detail.isEmpty()) {
            logger.info(stockMeta.ticker + " : " + stockMeta.price);
            stockMetaDetailRepository.save(new StockMetaDetail(stockMeta));
        }
    }

    /**
     * 증권사 내 포트폴리오 sync
     */
    public void portfolioSync() {
        try {
            seleniumService.setDriver();
            login();
            parseMyPortfolioMain(seleniumService.getDriver(), seleniumService.getWait());
            parseStockPortfolio(seleniumService.getDriver());
        } catch (Exception e) {
            logger.error(e.toString());
            seleniumService.getDriver().close();
            throw e;
        }
        seleniumService.getDriver().close();
    }

    /**
     * My 자산 크롤링
     */
    private void parseMyPortfolioMain(WebDriver webDriver, WebDriverWait webDriverWait) {
        //My자산
        ((JavascriptExecutor) webDriver).executeScript("openHp('/hkd/hkd1001/r01.do', true);");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hkd1001a01Tbody")));
        String source = webDriver.getPageSource();
        Document document = Jsoup.parse(source);
        ArrayList<MyPortfolioDTO> response = mrParsingService.parseMyPortfolio(document);
        portfolioService.deleteByPortfolioType(PortfolioTypeEnum.MIRAEA_ASSET);
        for (MyPortfolioDTO dto : response) {
            portfolioService.save(dto.name, dto.currentPrice, PortfolioTypeEnum.MIRAEA_ASSET);
        }
    }

    /**
     * 미래에셋대우 로그인
     */
    private void login() {
        seleniumService.getDriver().get("https://securities.miraeasset.com/login/form.do");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        Util.sleep(1000);
        seleniumService.getDriver().findElement(By.name("usid")).sendKeys("joon8409");
        Util.sleep(3500);
        //로그인 버튼 클릭
        ((JavascriptExecutor) seleniumService.getDriver()).executeScript("doSubmit();");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
    }

    /**
     * 현재 주식 보유량 크롤링
     */
    private void parseStockPortfolio(WebDriver driver) {
        //My자산
        ((JavascriptExecutor) driver).executeScript("openHp('/hkd/hkd1001/r01.do', true);");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        //상품별 자산
        ((JavascriptExecutor) driver).executeScript("javascript:openHp('/hkd/hkd1003/r01.do', false)");
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("gnb")));
        //주식 탭
        ((JavascriptExecutor) driver).executeScript("javascript:move('03')");
        String source = seleniumService.getDriver().getPageSource();
        Document document = Jsoup.parse(source, "ecu-kr");
        /*원화환산표시 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("exchangeWon")));
        driver.findElement(By.id("exchangeWon")).click();
        /*종목명 기달리기*/
        seleniumService.getWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("excelTable"), By.className("l")));
        /*기달리고 다시 가져오기*/
        source = driver.getPageSource();
        document = Jsoup.parse(source);
        Element element = document.select("#excelTable").first();
        /*투자 내역 파싱*/
        ArrayList<StockPortfolio> response = mrParsingService.parse(document);
        //long sum = 0;
        for (StockPortfolio s : response) {
            List<StockMeta> metas = stockMetaRepository.findByTicker(s.ticker);
            if (metas.size() == 0) {
                StockMeta stockMeta = new StockMeta();
                stockMeta.ticker = s.ticker;
                stockMeta.id = s.ticker;
                stockMeta.location = s.location;
                stockMetaRepository.save(stockMeta);
            }
            //sum += s.currentPriceSum;
        }
        /*이전 내역 삭제*/
        deleteByType(TypeConst.STOCK);
        save(response);
        //portfolioService.save("주식", sum);
    }
}
