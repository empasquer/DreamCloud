package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Profile> getProfiles() {
        String query = "SELECT * FROM profile;";
        RowMapper<Profile> rowMapper = new BeanPropertyRowMapper<>(Profile.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public void createProfile(String profileFirstName, String profileLastName, String profileUsername, String profilePassword, Optional<byte[]> profilePicture) {
        String query = "INSERT INTO profile(profile_firstname, profile_lastname, profile_username, profile_password, profile_picture) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query, profileFirstName, profileLastName, profileUsername, profilePassword, profilePicture);
    }


    public Profile getProfileFromUsername(String profileUsername) {
        String query = "SELECT * FROM profile WHERE profile_username = ?";
        RowMapper<Profile> rowMapper = new BeanPropertyRowMapper<>(Profile.class);
        try {
            return jdbcTemplate.queryForObject(query, rowMapper, profileUsername);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
