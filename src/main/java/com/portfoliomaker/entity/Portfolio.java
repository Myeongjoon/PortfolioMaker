package com.portfoliomaker.entity;

import com.portfoliomaker.e.BuySellType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Portfolio {

    /**
     * 포트폴리오 타입 이름.
     */
    @Id
    public String name;

    /**
     * 포트폴리오 가격
     */
    public double price;

    public Portfolio() {

    }

    public Portfolio(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
