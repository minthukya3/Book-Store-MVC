package com.example.bookstoremvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Throwable {
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.formLogin(c -> c.loginPage("/login")
                .failureUrl("/login-error"));
        httpSecurity.logout(c -> c.logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll());
        httpSecurity.authorizeHttpRequests(c -> c
                .requestMatchers("/", "/home","/book-details","/list-all-books","images/**","/view-cart",
                        "/add-to-cart","/add-to-cart-index","/add-to-cart-more","/remove-cart-item")
                .permitAll()
                .anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService service() {
        var uds = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("min").password("7890").authorities("write","read").build();
        uds.createUser(user1);
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
