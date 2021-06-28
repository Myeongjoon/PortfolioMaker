package com.portfoliomaker.entity;

import com.portfoliomaker.e.BuySellType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BuySellLog {
    @Id
    public String id;

    /**
     * 예 : T
     */
    public String ticker;

    /**
     * 구매, 판매
     */
    public BuySellType type;
}
