package com.portfoliomaker.entity.portfolio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortfolioType {

    /**
     * 포트폴리오 타입 이름.
     */
    @Id
    public String name;
}
