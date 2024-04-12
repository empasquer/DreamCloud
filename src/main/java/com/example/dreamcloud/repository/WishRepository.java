package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
