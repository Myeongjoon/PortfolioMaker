package com.portfoliomaker.entity.portfolio;

import javax.persistence.*;
import java.util.Date;

/**
 * 포트폴리오의 변화 기록
 */
@Entity
@Table(
        indexes = {@Index(name = "name_index", columnList = "name")}
)
public class PortfolioDetail {
    @Id
    public String id;

    /**
     * 포트폴리오 타입 이름.
     */
    public String name;

    /**
     * 변화 날짜
     */
    public Date date;

    /**
     * 포트폴리오 가격
     */
    public long price;

    public String hash() {
        return name + date;
    }
}
