package com.y.Y.config;

import com.y.Y.features.auth.CustomAuthenticationManager;
import com.y.Y.features.user.user_details.CustomUserDetailsService;
import com.y.Y.filters.CustomSessionAuthenticationFilter;
import com.y.Y.filters.ExceptionHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration()
public class SecurityConfiguration{

        @Autowired
        private CustomUserDetailsService userDetailsService;

        public static final String[] PUBLIC_URLS = {"/api/auth/login", "/api/auth/logout", "/api/users/register"};

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .cors(AbstractHttpConfigurer::disable)
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement((sess) -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/**").permitAll())
                        .httpBasic(withDefaults())
                        .authenticationManager(authManager())
                        .addFilterBefore(exceptionHandlerFilter(), DisableEncodeUrlFilter.class)
                        .addFilterBefore(customSessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authManager() {
                CustomAuthenticationManager authenticationManager = new CustomAuthenticationManager();
                authenticationManager.setPasswordEncoder(encoder());
                authenticationManager.setUserDetailsService(userDetailsService);

                return authenticationManager;
        }

        @Bean
        public CustomSessionAuthenticationFilter customSessionAuthenticationFilter() {
                return new CustomSessionAuthenticationFilter(authManager());
        }

        @Bean
        public ExceptionHandlerFilter exceptionHandlerFilter() {
                return new ExceptionHandlerFilter();
        }

        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }
}
