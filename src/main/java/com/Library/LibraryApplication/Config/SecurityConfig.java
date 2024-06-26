package com.Library.LibraryApplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable();

        return http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/restaurants").hasAnyRole("client","admin")
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").hasAnyRole("client","admin")
                        .requestMatchers("/restaurants/create").hasRole("admin")
                        .requestMatchers("/restaurants/edit").hasRole("admin")
                        .requestMatchers("/restaurants/createmeal").hasRole("admin")
                        .requestMatchers("/restaurants/editmeal").hasRole("admin")
                        .anyRequest().permitAll()

                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/restaurants", true)
                )
                .logout(config -> config.logoutSuccessUrl("/"))
                .build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
