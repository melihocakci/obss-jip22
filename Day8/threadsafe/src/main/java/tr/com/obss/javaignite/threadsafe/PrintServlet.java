package tr.com.obss.javaignite.threadsafe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        if (req.getRequestURL().toString().contains("security")) {
            resp.sendError(403);
        } else {

            PrintWriter out = resp.getWriter();
            out.println(req.getRequestURL().toString() + "<br>");
            out.println(req.getRequestURI() + "<br>");
            out.println(req.getServletPath() + "<br>");
            out.println(req.getContextPath() + "<br>");
            out.println(req.getPathInfo() + "<br>");
            out.println(req.getPathTranslated() + "<br>");
        }
    }
}
