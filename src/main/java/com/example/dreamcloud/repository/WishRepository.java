package com.example.dreamcloud.repository;

import com.example.dreamcloud.model.Profile;
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
        String query = "SELECT * FROM wish w\n" +
                "LEFT JOIN reservation r ON w.wish_id = r.wish_id\n" +
                "left JOIN profile p ON r.profile_username = p.profile_username" +
                " where w.wish_id=?";

        return jdbcTemplate.queryForObject(query, new Object[]{wishId}, (rs, rowNum) -> {
            Wish wish = new Wish();
            wish.setWishId(rs.getInt("wish_id"));
            wish.setWishName(rs.getString("wish_name"));
            wish.setWishDescription(rs.getString("wish_description"));
            wish.setWishPrice(rs.getDouble("wish_price"));
            wish.setWishPicture(rs.getBytes("wish_picture"));
            wish.setWishlistId(rs.getInt("wishlist_id"));


            // Check if there is a profile associated with the wish
            String profileUsername = rs.getString("profile_username");
            if (profileUsername != null) {
                Profile profile = new Profile();
                profile.setProfileUsername(profileUsername);
                wish.setWishReservedBy(profile);
                wish.setWishIsReserved(true);
            } else {
                // No profile associated with the wish
                wish.setWishIsReserved(false);
            }

            return wish;
        });
    }


    public void deleteWishFromWishId(int wishId) {
        String query = "DELETE FROM wish WHERE wish_id = ?;";
        jdbcTemplate.update(query, wishId);
    }

// is this being used?
    public void createProfile( String profileUsername, String profileFirstName, String profileLastName, String profilePassword, Optional<byte[]> profilePicture) {
        String query = "INSERT INTO profile(profile_username, profile_firstname, profile_lastname, profile_password, profile_picture) VALUES (?,?,?,?,?)";

        //Convert the picture to a byte array if exists
        byte[] pictureData = profilePicture.orElse(null);

        jdbcTemplate.update(query, profileUsername, profileFirstName, profileLastName, profilePassword, pictureData);
    }

    public void createWish(String name, String description, double price, Optional<byte[]> wishPicture, int wishlistId) {
        String query = "INSERT INTO wish(wish_name, wish_description, wish_price, wish_picture, wish_is_reserved, wishlist_id) VALUES (?,?,?,?,?,?);";

        // Convert the wishPicture to a byte array if exists
        byte[] pictureData = wishPicture.orElse(null);

        jdbcTemplate.update(query, name, description, price, pictureData, false, wishlistId);
    }

    public void reserveWish(String reservedByUsername, int wishId) {
        String query = "INSERT INTO reservation (wish_id, profile_username) VALUES (?, ?)";
        jdbcTemplate.update(query, wishId, reservedByUsername);
    }


    public void unReserveWish( int wishId) {
        String query = "DELETE FROM reservation where wish_id  =?";
        jdbcTemplate.update(query, wishId);
    }


}
