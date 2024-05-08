package com.y.Y.config;

import com.y.Y.features.auth.CustomAuthenticationManager;
import com.y.Y.features.session.SessionService;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import com.y.Y.filters.CustomSessionAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfiguration {

        private final CustomAuthenticationManager authenticationManager;
        private final SessionService sessionService;

        @Autowired
        public SecurityConfiguration(CustomUserDetailsService userDetailsService, CustomAuthenticationManager authenticationProvider, SessionService sessionService){
            this.authenticationManager = authenticationProvider;
            this.sessionService = sessionService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/api/auth/login", "/api/auth/logout", "api/auth/create").permitAll()
                                .requestMatchers("/api/**").authenticated())
                        .httpBasic(withDefaults())
                        .authenticationManager(authenticationManager)
                        .addFilterBefore(new CustomSessionAuthenticationFilter(sessionService, authenticationManager), UsernamePasswordAuthenticationFilter.class);


                return http.build();
        }
}
