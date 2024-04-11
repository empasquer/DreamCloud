package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    public List<Wishlist> getWishlists() {
        return wishlistRepository.getWishlists();
    }

    public Wishlist getWishlistFromProfileId(int wishlistId) {
        return wishlistRepository.getWishlistFromId(wishlistId);
    }
}
