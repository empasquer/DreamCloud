package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    @Autowired
    WishRepository wishRepository;


    public List<Wish> getWishes() {
        return wishRepository.getWishes();
    }
}
