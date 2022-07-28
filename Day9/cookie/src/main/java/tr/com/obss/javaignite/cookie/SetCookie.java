package tr.com.obss.javaignite.cookie;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "set-cookie", value = "/set-cookie")
public class SetCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("value");

        Cookie cookie = new Cookie("example-cookie", value);
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/get-cookie");
    }
}