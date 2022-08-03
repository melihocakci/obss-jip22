package tr.com.obss.javaignite.threadsafe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ContactsServlet extends HttpServlet {
    private final String connectionString = "jdbc:postgresql://localhost:5432/servlet";
    private final String username = "postgres";
    private final String password = "727";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(connectionString, username, password);

            resp.setContentType("text/html");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM contacts WHERE name = ?");
            st.setString(1, req.getParameter("name"));

            ResultSet rs = st.executeQuery();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<table border=\"1\"> " +
                    "    <tr>" +
                    "    <th>id</th>" +
                    "    <th>name</th>" +
                    "    <th>tel_number</th>" +
                    "    <th>edit</th>" +
                    "  </tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                name = name.trim();
                String tel_number = rs.getString("tel_number");

                stringBuilder.append("<tr>");
                stringBuilder.append("<td>").append(id).append("</td>");
                stringBuilder.append("<td>").append(name).append("</td>");
                stringBuilder.append("<td>").append(tel_number).append("</td>");
                stringBuilder.append("<td>")
                        .append("<a href=\"edit-contacts.jsp?id=").append(id)
                        .append("&name=").append(name)
                        .append("&tel_number=").append(tel_number)
                        .append("\">Edit</a>")
                        .append("</td>");
                stringBuilder.append("</tr>");
            }
            stringBuilder.append("</table>");

            req.setAttribute("table", stringBuilder.toString());

            req.getRequestDispatcher("find-contacts.jsp").forward(req, resp);

        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.println("Contact creation failed");
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(connectionString, username, password);

            resp.setContentType("text/html");
            PreparedStatement st = conn.prepareStatement("INSERT INTO contacts(name, tel_number) VALUES(?, ?)");
            st.setString(1, req.getParameter("name"));
            st.setString(2, req.getParameter("tel_number"));

            st.executeUpdate();

            PrintWriter out = resp.getWriter();
            out.println("Contact created");
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.println("Contact creation failed");
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
