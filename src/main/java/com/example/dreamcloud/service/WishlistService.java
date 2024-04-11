package com.example.dreamcloud.service;

import com.example.dreamcloud.model.Wishlist;
import com.example.dreamcloud.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    public List<Wishlist> getWishlists() {
        return wishlistRepository.getWishlists();
    }

    public Wishlist getWishlistFromWishlistId(int wishlistId) {
        return wishlistRepository.getWishlistFromWishlistId(wishlistId);
    }

    public List<Wishlist> getWishlistsFromProfileUsername(String profileUsername) {
        return wishlistRepository.geWishlistsFromProfileUsername(profileUsername);
    }
}
