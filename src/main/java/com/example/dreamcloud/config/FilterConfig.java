package com.example.dreamcloud.config;

import com.example.dreamcloud.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    //This is how Spring creates bean authenticationFilterRegistration
    //FilterRegistration of type AuthenticationFilter in which we define what the filter should do.
    //Look at AuthenticationFilter to see how we check if the user is logged in or if the URL username
    //matches the session username.
    //So here we tell springboot where to apply the filter, and it needs instructions to create the bean.

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        // make registrationBean and set a filter
        registrationBean.setFilter(new AuthenticationFilter());

        //where should the filter be applied:
        registrationBean.addUrlPatterns("/profile/*"); // Apply the filter only to profile-related endpoints
        // how high should this filter be - we only have one for now
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
