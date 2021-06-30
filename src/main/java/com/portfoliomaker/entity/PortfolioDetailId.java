package com.portfoliomaker.entity;

import java.io.Serializable;
import java.util.Date;

public class PortfolioDetailId implements Serializable {

    /**
     * 포트폴리오 타입 이름.
     */
    public String name;

    /**
     * 변화 날짜
     */
    public Date date;
}
