package tr.com.obss.jip.springdemo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.info("Request has started: {}", httpServletRequest.getRequestURL().toString());
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("Request has ended: {}", httpServletRequest.getRequestURL().toString());
    }
}

