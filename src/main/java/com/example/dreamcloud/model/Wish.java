package com.example.dreamcloud.model;

import java.util.Base64;
import java.util.Optional;

public class Wish {

    private int wishId;
    private String wishName;
    private String wishDescription;
    private Double wishPrice;
    private byte[] wishPicture; // Add this field
    private int wishlistId;

    public Wish() {
    }

    public int getWishId() {
        return wishId;
    }

    public String getWishName() {
        return wishName;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public Double getWishPrice() {
        return wishPrice;
    }

    public byte[] getWishPicture() {
        return wishPicture;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }

    public void setWishPrice(Double wishPrice) {
        this.wishPrice = wishPrice;
    }

    public void setWishPicture(byte[] wishPicture) {
        this.wishPicture = wishPicture;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishPictureAsBase64() {
        if (wishPicture != null) {
            return Base64.getEncoder().encodeToString(wishPicture);
        } else {
            return null;
        }
    }
}
