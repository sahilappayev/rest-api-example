package com.example.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity security) throws Exception {
        return security
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(HttpMethod.GET, "/categories", "/v3/api-docs/**", "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories/{id}", "/categories/names").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/categories/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .build();
    }

//    @Bean
//    public UserDetailsService users(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("sahil")
//                .password(passwordEncoder.encode("56789"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("togrul")
//                .password(passwordEncoder.encode("54321"))
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.jdbcAuthentication();
//        return authenticationManagerBuilder.build();
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://example.com", "http://127.0.0.1:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
