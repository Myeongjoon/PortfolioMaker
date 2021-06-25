package com.portfoliomaker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Main {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
}
