package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {
    @Autowired
    WishRepository wishRepository;


    public List<Wish> getWishes() {
        return wishRepository.getWishes();
    }

    public List<Wish> getWishesFromWishListId(int wishlistId) {
        return wishRepository.getWishesFromWishlistId(wishlistId);
    }

    public Wish getWishFromWishId(int wishId) {
        return wishRepository.getWishFromWishId(wishId);
    }

    public void deleteWishFromWishId(int wishId) {
        wishRepository.deleteWishFromWishId(wishId);
    }

    public void createWish(String name, String description, double price, Optional<byte[]> wishPicture, int wishlistId) {
        wishRepository.createWish(name, description, price, wishPicture, wishlistId);
    }
}
