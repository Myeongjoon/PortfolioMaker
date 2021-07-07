package com.portfoliomaker.service;

import com.portfoliomaker.dto.PortfolioDTO;
import com.portfoliomaker.dto.PortfolioDetailDTO;
import com.portfoliomaker.e.PortfolioTypeEnum;
import com.portfoliomaker.entity.portfolio.Portfolio;
import com.portfoliomaker.entity.portfolio.PortfolioDetail;
import com.portfoliomaker.entity.portfolio.PortfolioType;
import com.portfoliomaker.repository.portfolio.PortfolioDetailRepository;
import com.portfoliomaker.repository.portfolio.PortfolioRepository;
import com.portfoliomaker.repository.portfolio.PortfolioTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    PortfolioDetailRepository portfolioDetailRepository;
    @Autowired
    PortfolioTypeRepository portfolioTypeRepository;

    /**
     * 금액 변동이 없으면 추가 하지 않음.
     *
     * @param detail
     */
    public void insert(PortfolioDetail detail) {
        List<PortfolioDetail> portfolio = portfolioDetailRepository.findTop1ByNameOrderByDateDesc(detail.name);
        if (portfolio.size() == 1 && portfolio.get(0).price == detail.price) {
            return;
        }
        if (detail.id == null) {
            detail.id = detail.hash();
        }
        portfolioDetailRepository.save(detail);
    }

    public List<PortfolioDetailDTO> getDetail(String name) {
        List<PortfolioDetail> data = portfolioDetailRepository.findByNameOrderByDateDesc(name);
        List<PortfolioDetailDTO> response = new ArrayList<>();
        for (PortfolioDetail p : data) {
            PortfolioDetailDTO dto = new PortfolioDetailDTO(p);
            response.add(dto);
        }
        return response;
    }

    public List<PortfolioDTO> getPortfolio() {
        List<Portfolio> data = portfolioRepository.findAll();
        List<PortfolioDTO> response = new ArrayList<>();
        if(data.isEmpty()){
            return response;
        }
        String detail = "";
        long sum = 0;
        for (Portfolio p : data) {
            sum += p.price;
            PortfolioDTO dto = new PortfolioDTO(p);
            response.add(dto);
            detail += p.price + "+";
        }
        detail = detail.substring(0, detail.length() - 1);
        PortfolioDTO sum_port = new PortfolioDTO("합계", sum);
        response.add(sum_port);
        //합계 저장을 위해 합계 저장
        PortfolioDetail sumDetail = new PortfolioDetail();
        sumDetail.date = new Date();
        sumDetail.price = sum;
        sumDetail.name = "합계";
        sumDetail.detail = detail;
        insert(sumDetail);
        return response;
    }

    public List<PortfolioType> getPortfolioTypes() {
        return portfolioTypeRepository.findAll();
    }

    public List<Portfolio> findAll() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        portfolios.sort((o1, o2) -> {
            if (o1.price < o2.price) {
                return 1;
            } else if (o1.price > o2.price) {
                return -1;
            } else {
                return 0;
            }
        });
        return portfolios;
    }

    public void deleteByPortfolioType(PortfolioTypeEnum type) {
        for (Portfolio p : portfolioRepository.findByType(type)) {
            portfolioRepository.delete(p);
        }
    }

    /**
     * 포트폴리오 업데이트 및 디테일 업데이트 - 금액이 바뀌지 않으면 업데이트 하지 않음.
     *
     * @param name
     * @param price
     */
    public void save(String name, long price, PortfolioTypeEnum portfolioTypeEnum) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(name);
        if (portfolio.isPresent() && portfolio.get().price == price) {
            return;
        }
        portfolioRepository.save(new Portfolio(name, price, portfolioTypeEnum));
        PortfolioDetail detail = new PortfolioDetail();
        detail.name = name;
        detail.price = price;
        detail.date = new Date();
        insert(detail);
    }


    /**
     * 포트폴리오 업데이트 및 디테일 업데이트 - 금액이 바뀌지 않으면 업데이트 하지 않음.
     *
     * @param name
     * @param price
     */
    public void save(String name, long price) {
        save(name, price, PortfolioTypeEnum.UNKNOWN);
    }

    public void saveType(String name) {
        PortfolioType type = new PortfolioType();
        type.name = name;
        portfolioTypeRepository.save(type);
    }

    public void deleteDetail(String id) {
        portfolioDetailRepository.deleteById(id);
    }

    public void deleteById(String id) {
        portfolioRepository.deleteById(id);
    }
}
