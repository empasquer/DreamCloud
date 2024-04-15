package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Wishlist> getWishlists() {
        String query = "SELECT * FROM wishlist;";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public Wishlist getWishlistFromWishlistId(int wishlistId) {
        String query = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return jdbcTemplate.queryForObject(query, rowMapper, wishlistId);
    }

    public List<Wishlist> geWishlistsFromProfileUsername(String profileUsername) {
        String query = "SELECT * FROM wishlist WHERE profile_username = ?;";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return jdbcTemplate.query(query, rowMapper, profileUsername);
    }

    public void createWishlist(String title, String description, String profileUsername) {
        String query = "INSERT INTO wishlist(wishlist_title, wishlist_description, profile_username) VALUES (?,?,?)";
        jdbcTemplate.update(query, title, description, profileUsername);
    }

    public void deleteWishlist(int wishlistId) {
        String query = "DELETE FROM wishlist WHERE wishlist_id = ?";
        jdbcTemplate.update(query,wishlistId);
    }
}
