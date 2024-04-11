package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Wish;
import com.example.dreamcloud.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    public Wishlist getWishlistFromId(int wishlistId) {
        String query = "SELECT * FROM wishlist WHERE wishlistId = ?";
        RowMapper<Wishlist> rowMapper = new BeanPropertyRowMapper<>(Wishlist.class);
        return jdbcTemplate.queryForObject(query, rowMapper, wishlistId);
    }
}
