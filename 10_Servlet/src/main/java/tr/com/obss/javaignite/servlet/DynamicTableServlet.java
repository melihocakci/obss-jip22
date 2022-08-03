package tr.com.obss.javaignite.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "dynamic-table", value = "/dynamic-table")
public class DynamicTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> list = new ArrayList<>();
        list.add("Company");
        list.add("Contact");
        list.add("Country");
        list.add("Alfreds Futterkiste");
        list.add("Maria Anders");
        list.add("Germany");
        list.add("Centro comercial Moctezuma");
        list.add("Francisco Chang");
        list.add("Mexico");

        req.setAttribute("list", list);

        req.getRequestDispatcher("dynamic-table.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
