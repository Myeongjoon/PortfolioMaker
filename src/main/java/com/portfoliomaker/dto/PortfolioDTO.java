package com.portfoliomaker.dto;

import com.portfoliomaker.entity.Portfolio;

import java.text.NumberFormat;
import java.util.Locale;

public class PortfolioDTO {

    /**
     * 포트폴리오 타입 이름.
     */
    public String name;

    /**
     * 포트폴리오 가격
     */
    public String price;

    public PortfolioDTO() {

    }

    public PortfolioDTO(String name, long price) {
        this.name = name;
        this.price = NumberFormat.getNumberInstance(Locale.US).format(price);
    }

    public PortfolioDTO(Portfolio p) {
        this.name = p.name;
        this.price = NumberFormat.getNumberInstance(Locale.US).format(p.price);
    }
}
