package tr.com.obss.javaignite.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String username_init = getServletConfig().getInitParameter("username-init");
        String password_init = getServletConfig().getInitParameter("password-init");

        if (username.equals(username_init) && password.equals(password_init)) {
            req.getRequestDispatcher("success.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("failure.jsp").forward(req, resp);
        }
    }
}
