package tr.com.obss.jip.bookportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tr.com.obss.jip.bookportal.other.MyUserDetails;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {

        return http
                .cors()
                .and()

                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user/read/**", "api/user/favorite/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/user", "api/user/").not().authenticated()
                .antMatchers(HttpMethod.PUT, "/api/user/{\\d+}/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/user/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/user/{\\d+}/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/user/**").authenticated()

                //.antMatchers(HttpMethod.GET, "/api/book/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/book/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/book/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/book/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()

                .formLogin().loginProcessingUrl("/basicauth").permitAll().and()

                .logout().deleteCookies().clearAuthentication(true)
                .permitAll()
                .and()

                .httpBasic()
                .and()

                .authenticationProvider(daoAuthenticationProvider)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
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
}
