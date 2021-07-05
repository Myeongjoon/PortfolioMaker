package com.portfoliomaker.dto;

import com.portfoliomaker.entity.portfolio.PortfolioDetail;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class PortfolioDetailDTO {

    public String id;

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

    /**
     * 세부 내용
     */
    public String detail;

    public PortfolioDetailDTO() {

    }

    public PortfolioDetailDTO(PortfolioDetail p) {
        this.id = p.id;
        this.date = p.date;
        this.name = p.name;
        this.price = NumberFormat.getNumberInstance(Locale.US).format(p.price);
        this.detail = p.detail;
    }
}
