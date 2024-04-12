package com.example.dreamcloud.model;

import java.util.ArrayList;

public class Wishlist {

    private int wishlistId;
    private String wishlistTitle;
    private String wishlistDescription;
    private String profileUsername;
    private ArrayList<Wish> wishes;

    public Wishlist() {
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public String getWishlistTitle() {
        return wishlistTitle;
    }

    public String getWishlistDescription() {
        return wishlistDescription;
    }

    public String getProfileUsername() {
        return profileUsername;
    }

    public ArrayList<Wish> getWishes() {
        return wishes;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setWishlistTitle(String wishlistTitle) {
        this.wishlistTitle = wishlistTitle;
    }

    public void setWishlistDescription(String wishlistDescription) {
        this.wishlistDescription = wishlistDescription;
    }

    public void setProfileUsername(String profileUsername) {
        this.profileUsername = profileUsername;
    }

    public void setWishes(ArrayList<Wish> wishes) {
        this.wishes = wishes;
    }

}
