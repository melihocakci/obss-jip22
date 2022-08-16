package tr.com.obss.jip.bookportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tr.com.obss.jip.bookportal.component.JwtAuthenticationEntryPoint;
import tr.com.obss.jip.bookportal.filter.JwtRequestFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/read/**", "/api/user/favorite/**")
                .authenticated()
                .antMatchers(HttpMethod.POST, "/api/user*")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user/{\\d+}/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/user/**")
                .authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/user/{\\d+}/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/user/**")
                .authenticated()
                .antMatchers(HttpMethod.POST, "/api/book/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/book/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/book/**")
                .hasRole("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
