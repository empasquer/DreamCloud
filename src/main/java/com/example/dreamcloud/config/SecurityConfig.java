package com.example.dreamcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.sql.DataSource;

@Configuration //defines @Bean methods.
@EnableWebSecurity // supports web Spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource; //database connection
    private final PasswordEncoder passwordEncoder; // encodes/decodes passwords

    @Autowired
    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    //why is it protected?
    @Override
    //This is where we define how we authenticate users.
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) // now we specify that the dataSource is a JDBC
                .usersByUsernameQuery("SELECT profile_username, profile_password  FROM profile where profile_username = ?")
                .passwordEncoder(passwordEncoder);
    }


    // A lot of beans were missing, so we have created them ourselves. Could be a problem with
    //our project setup or the dependencies, but we couldn't find them.

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}