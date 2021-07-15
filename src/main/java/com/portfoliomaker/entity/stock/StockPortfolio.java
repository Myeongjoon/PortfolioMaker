package com.portfoliomaker.entity.stock;

import com.portfoliomaker.dto.stock.StockPortfolioDTO;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "StockPortfolioMapping",
        classes = @ConstructorResult(
                targetClass = StockPortfolioDTO.class,
                columns = {
                        @ColumnResult(name = "current_price_sum", type = Double.class),
                        @ColumnResult(name = "buy_price_sum", type = Double.class),
                        @ColumnResult(name = "ticker", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "count", type = String.class),
                        @ColumnResult(name = "previous_rate", type = String.class),
                        @ColumnResult(name = "sector", type = String.class)
                })
)
@Entity
public class StockPortfolio {
    /**
     * 주식 id
     */
    @Id
    public String ticker;

    /**
     * 보유 잔고
     */
    public long count;

    /**
     * 구매 가격 합산
     */
    public Long buyPriceSum;

    /**
     * 예 : 주식, 펀드
     */
    public String type;

    /**
     * 현재 가격 합산
     */
    public Long currentPriceSum;

    /**
     * 주식 위치
     */
    public String location;

    /**
     * 전일 대비 상승량
     */
    public Double previousRate;
}
