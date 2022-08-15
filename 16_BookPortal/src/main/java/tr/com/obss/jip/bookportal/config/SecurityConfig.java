package tr.com.obss.jip.bookportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.MyUserDetails;
import tr.com.obss.jip.bookportal.service.UserService;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final UserService userService;

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final UserDetailsService jwtUserDetailsService;

  private final JwtRequestFilter jwtRequestFilter;

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {

    return http.cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/api/user/read/**", "/api/user/favorite/**")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/api/user", "api/user/")
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
        .logout()
        .permitAll()
        .and()
        .httpBasic()
        .and()
        .authenticationProvider(daoAuthenticationProvider)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(
      PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      final User user = userService.getUser(username);
      List<String> role = List.of(user.getRole().getName().toString());
      return new MyUserDetails(user.getUsername(), user.getPassword(), role);
    };
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE");
      }
    };
  }
}
