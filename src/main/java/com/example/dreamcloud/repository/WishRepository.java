package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WishRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Wish> getWishes() {
        String query = "SELECT * FROM wish;";
        RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Wish> getWishesFromWishlistId(int wishlistId) {
        String query = "SELECT * FROM wish WHERE wishlist_id = ?;";
        RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);
        return jdbcTemplate.query(query, rowMapper, wishlistId);
    }

    public Wish getWishFromWishId(int wishId) {
        String query = "SELECT * FROM wish WHERE wish_id = ?;";
        RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);
        return jdbcTemplate.queryForObject(query, rowMapper, wishId);
    }

    public void deleteWishFromWishId(int wishId) {
        String query = "DELETE FROM wish WHERE wish_id = ?;";
        jdbcTemplate.update(query, wishId);
    }


    public void createProfile( String profileUsername, String profileFirstName, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        String query = "INSERT INTO profile(profile_username, profile_firstname, profile_lastname, profile_password, profile_picture) VALUES (?,?,?,?,?)";

        //Convert the picture to a byte array if exists
        byte[] pictureData = profilePicture.orElse(null);

        jdbcTemplate.update(query, profileUsername, profileFirstName, profileLastName, profilePassword, pictureData);
    }

    public void createWish(String name, String description, double price, Optional<byte[]> wishPicture, int wishlistId) {
        String query = "INSERT INTO wish(wish_name, wish_description, wish_price, wish_picture, wishlist_id) VALUES (?,?,?,?,?);";

        // Convert the wishPicture to a byte array if exists
        byte[] pictureData = wishPicture.orElse(null);

        jdbcTemplate.update(query, name, description, price, pictureData, wishlistId);
    }

    public void editWish(int wishId, String name, String description, double price, Optional<byte[]> wishPicture) {
        String query = "UPDATE wish SET wish_name = ?, wish_description = ?, wish_price = ?, wish_picture = ? WHERE wish_id = ?";

        // Convert the wishPicture to a byte array if it exists
        byte[] pictureData = wishPicture.orElse(null);

        jdbcTemplate.update(query, name, description, price, pictureData, wishId);
    }

}
