package com.portfoliomaker.dto;

import com.portfoliomaker.entity.Portfolio;
import com.portfoliomaker.entity.PortfolioDetail;

import javax.persistence.Id;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class PortfolioDetailDTO {

    /**
     * 포트폴리오 타입 이름.
     */
    public String name;

    /**
     * 포트폴리오 가격
     */
    public String price;

    /**
     * 변화 날짜
     */
    public Date date;

    public PortfolioDetailDTO() {

    }

    public PortfolioDetailDTO(String name, long price) {
        this.name = name;
        this.price = NumberFormat.getNumberInstance(Locale.US).format(price);
    }

    public PortfolioDetailDTO(PortfolioDetail p) {
        this.date = p.date;
        this.name = p.name;
        this.price = NumberFormat.getNumberInstance(Locale.US).format(p.price);
    }
}
