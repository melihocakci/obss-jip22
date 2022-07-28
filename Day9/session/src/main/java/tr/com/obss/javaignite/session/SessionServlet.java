package tr.com.obss.javaignite.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "session-servlet", value = "/session-servlet")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username.equals("melih") && password.equals("123")) {
            HttpSession session = req.getSession();
            session.setAttribute("isUserLoggedIn", true);
            resp.sendRedirect(req.getContextPath() + "/private/home.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/public/login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.removeAttribute("isUserLoggedIn");

        resp.sendRedirect(req.getContextPath());
    }
}
