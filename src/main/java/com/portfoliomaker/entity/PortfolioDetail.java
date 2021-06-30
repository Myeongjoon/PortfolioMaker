package com.portfoliomaker.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 포트폴리오의 변화 기록
 */
@Entity
@IdClass(PortfolioDetailId.class)
@Table(
        indexes = {@Index(name = "name_index", columnList = "name")}
)
public class PortfolioDetail {

    /**
     * 포트폴리오 타입 이름.
     */
    @Id
    public String name;

    /**
     * 변화 날짜
     */
    @Id
    public Date date;

    /**
     * 포트폴리오 가격
     */
    public long price;
}
