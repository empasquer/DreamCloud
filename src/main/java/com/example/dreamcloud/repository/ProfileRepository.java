package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Profile;
import com.example.dreamcloud.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Profile> getProfiles() {
        String query = "SELECT * FROM profile;";
        RowMapper<Profile> rowMapper = new BeanPropertyRowMapper<>(Profile.class);
        return jdbcTemplate.query(query, rowMapper);
    }
}
