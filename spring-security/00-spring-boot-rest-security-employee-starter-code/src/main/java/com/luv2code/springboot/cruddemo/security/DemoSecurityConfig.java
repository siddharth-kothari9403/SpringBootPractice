package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {
    //adding users, passwords and roles

    //using a third party class, hence configuration bean
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails john = User.builder()
//                .username("john")
//                .password("{noop}test111")
//                .roles("EMPLOYEE")
//                .build();
//
//        UserDetails mary = User.builder()
//                .username("mary")
//                .password("{noop}test222")
//                .roles("EMPLOYEE", "MANAGER")
//                .build();
//
//        UserDetails susan = User.builder()
//                .username("susan")
//                .password("{noop}test333")
//                .roles("EMPLOYEE", "MANAGER", "ADMIN")
//                .build();
//
//        //the roles are custom defined
//        //the password {noop}-> stored in plain text, no operations, followed by the actual password
//
//        return new InMemoryUserDetailsManager(john, mary, susan);
//    }

    //datasource auto configured by spring boot
    //this gives us the user details manager for the database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //to configure your own security table, we need to set up these 2
        //to tell how to retrieve a user from the username
        //to tell how to determine authorization from the username
        jdbcUserDetailsManager.setUsersByUsernameQuery("select * from members where user_id=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select * from roles where user_id=?");
        return jdbcUserDetailsManager;
    }

    //permissions for each role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
        );

        //** -> wildcard meaning anything after that path can be accessed
        //we define the operations allowed to the given endpoints based on the roles that the user has
        //some can put, post, others can only see etc.

        //use basic http authentication
        http.httpBasic();

        //disable CSRF protection, not required for POST, PUT and DELETE operations
        http.csrf().disable();

        return http.build();
    }
}
