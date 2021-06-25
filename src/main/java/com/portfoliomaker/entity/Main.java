package com.portfoliomaker.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Main {
    @Id
    public String id;

    public String value;
}
