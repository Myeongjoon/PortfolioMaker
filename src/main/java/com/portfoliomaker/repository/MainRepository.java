package com.portfoliomaker.repository;

import com.portfoliomaker.entity.Main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRepository extends JpaRepository<Main, Long> {
}
