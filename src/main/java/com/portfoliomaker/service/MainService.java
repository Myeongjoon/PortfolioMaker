package com.portfoliomaker.service;

import com.portfoliomaker.entity.Main;
import com.portfoliomaker.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    MainRepository mainRepository;

    public List<Main> findAll() {
        return mainRepository.findAll();
    }

    public void save(Main main){
        mainRepository.save(main);
    }

    public void deleteAll(){
        mainRepository.deleteAll();
    }
}
