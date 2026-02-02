package com.example.OrganManagementSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);
        authenticationProvider.setPasswordEncoder(bcryptEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((configurer) -> {
                    configurer.requestMatchers(HttpMethod.POST, "/authenticate", "/register_user").permitAll()
                            .requestMatchers(HttpMethod.POST, "/register_doctor", "/register_admin").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/admin/doctors","/admin/patients","/admin/doctors/**","/admin/patients/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/user/viewMyInfo").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/user/updateMyInfo").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/user/addPatientInfo").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/recipient/viewInfo","/recipient/viewInfo/**").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/recipient/addInfo/**").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.PUT, "/recipient/updateInfo/**").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.GET, "/recipient/getAll").hasAnyRole("DOCTOR","ADMIN")
                            .requestMatchers(HttpMethod.GET, "/donor/viewInfo","/donor/viewInfo/**").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/donor/addInfo/**").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.GET, "/donor/getAll").hasAnyRole("DOCTOR","ADMIN")
                            .requestMatchers(HttpMethod.GET, "/doctor/viewPatients","/doctor/viewPatients/**","/doctor/viewMyInfo").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.POST, "/doctor/addMyInfo").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.PUT, "/doctor/updateMyInfo").hasRole("DOCTOR")
                            .requestMatchers(HttpMethod.GET, "/match/donor/**","/match/recipient/**","/match/patient/donor/**","/match/patient/recipient/**").hasAnyRole("DOCTOR","ADMIN","USER")
                            .anyRequest().authenticated();
                }).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilterConfig(), ChannelProcessingFilter.class);

        return http.build();
    }
}
