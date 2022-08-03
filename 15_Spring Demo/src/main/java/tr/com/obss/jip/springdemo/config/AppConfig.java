package tr.com.obss.jip.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tr.com.obss.jip.springdemo.filter.CustomFilter;
import tr.com.obss.jip.springdemo.interceptor.LogginInterceptor;
import tr.com.obss.jip.springdemo.model.Role;
import tr.com.obss.jip.springdemo.model.RoleType;
import tr.com.obss.jip.springdemo.model.User;
import tr.com.obss.jip.springdemo.service.RoleService;
import tr.com.obss.jip.springdemo.service.UserService;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final LogginInterceptor logginInterceptor;

    @Autowired
    public AppConfig(LogginInterceptor logginInterceptor) {
        this.logginInterceptor = logginInterceptor;
    }

    @Bean
    public FilterRegistrationBean<CustomFilter> customFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.setUrlPatterns(List.of("/api/admin/*"));
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logginInterceptor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5, new SecureRandom());
    }
}
