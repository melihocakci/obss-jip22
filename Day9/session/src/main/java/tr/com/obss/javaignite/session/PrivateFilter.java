package tr.com.obss.javaignite.session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "PrivateFilter", urlPatterns = {"/private/*"})
public class PrivateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("isUserLoggedIn");
        if (loggedIn == null || !loggedIn) {
            String requestURL = req.getRequestURL().toString();
            String[] strings = requestURL.split("/");

            String redirect = "";
            int i = strings.length - 1;
            while (!strings[i].equals("private")) {
                redirect = '/' + strings[i] + redirect;
                i--;
            }

            resp.sendRedirect(req.getContextPath() + "/public/login.jsp?redirect=/private" + redirect);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
