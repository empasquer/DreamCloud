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

    public Wishlist getWishlistFromWishlistId(int wishlistId) {
        return wishlistRepository.getWishlistFromWishlistId(wishlistId);
    }

    public List<Wishlist> getWishlistsFromProfileUsername(String profileUsername) {
        return wishlistRepository.geWishlistsFromProfileUsername(profileUsername);
    }

    public void createWishlist(String title, String description, String profileUsername) {
         wishlistRepository.createWishlist( title,  description,  profileUsername);
    }

    public void deleteWishlist(int wishlistId) {
         wishlistRepository.deleteWishlist(wishlistId);
    }

    public void editWishlist( String profileUsername, int wishlistId, String title, String description) {
        wishlistRepository.editWishlist(profileUsername, wishlistId, title, description);
    }
}
