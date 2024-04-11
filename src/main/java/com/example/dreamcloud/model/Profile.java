package com.example.dreamcloud.model;

import java.util.ArrayList;

public class Profile {
    private String profileUsername;
    private String profileFirstname;
    private String profileLastname;
    private String profilePassword;
    private ArrayList<Wishlist> wishlists;
    private byte[] profilePicture;

    public Profile() {
    }

    public String getProfileUsername() {
        return profileUsername;
    }

    public String getProfileFirstname() {
        return profileFirstname;
    }

    public String getProfileLastname() {
        return profileLastname;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public ArrayList<Wishlist> getWishlists() {
        return wishlists;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfileUsername(String profileUsername) {
        this.profileUsername = profileUsername;
    }

    public void setProfileFirstname(String profileFirstname) {
        this.profileFirstname = profileFirstname;
    }

    public void setProfileLastname(String profileLastname) {
        this.profileLastname = profileLastname;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }

    public void setWishlists(ArrayList<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
