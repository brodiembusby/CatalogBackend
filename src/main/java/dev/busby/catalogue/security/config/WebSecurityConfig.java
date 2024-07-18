package dev.busby.catalogue.security.config;

import dev.busby.catalogue.appuser.AppUserService;
import dev.busby.catalogue.security.authentication.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // You might be able to delete this
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(appUserService);
    }


    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/registration/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
//                    auth.requestMatchers("/api/v1/collectibles/**").permitAll();
//                    auth.requestMatchers("api/v1/piles").permitAll();
                    auth.anyRequest().authenticated();
                })
//                .formLogin(form -> form.disable())
//                .httpBasic(httpBasic -> httpBasic.disable())
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}
