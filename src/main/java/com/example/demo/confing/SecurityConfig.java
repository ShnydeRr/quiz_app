package com.example.demo.confing;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @SuppressWarnings("deprecation")

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)    throws Exception {
        return http
            .authorizeRequests(auth -> auth
              // Public pages
                .requestMatchers("/").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/contact").permitAll()
                // Pages accessible by normal_user
                .requestMatchers("/user/**").hasAuthority("normal_user")
                // Pages accessible by super_user
                .requestMatchers("/admin/**").hasAuthority("super_user")
                .requestMatchers("/quiz", "/submit-quiz").authenticated()  // Only authenticated users can take the quiz

                .anyRequest().authenticated()  // All other pages require authentication
            )
            .formLogin(form -> form
            .successHandler(customAuthenticationSuccessHandler)  // Use custom success handler
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/").permitAll()  // Redirect to home page after logout
            )
            .exceptionHandling(exceptions -> exceptions
            .accessDeniedPage("/access-denied")  // Custom access denied page
            )
            .build();
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Or any other encoder you prefer
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}