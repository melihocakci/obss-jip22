package tr.com.obss.javaignite.threadsafe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "editContact", value = "/edit-contact")
public class EditContactServlet extends HttpServlet {
    private final String connectionString = "jdbc:postgresql://localhost:5432/servlet";
    private final String username = "postgres";
    private final String password = "727";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(connectionString, username, password);

            resp.setContentType("text/html");
            PreparedStatement st = conn.prepareStatement("UPDATE contacts SET name = ?, tel_number = ? WHERE id = ?");
            st.setString(1, req.getParameter("name"));
            st.setString(2, req.getParameter("tel_number"));
            st.setInt(3, Integer.parseInt(req.getParameter("id")));

            st.executeUpdate();

            req.setAttribute("results", "Edit successful");
            req.getRequestDispatcher("edit-contacts.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("results", "Edit failed");
            req.getRequestDispatcher("edit-contacts.jsp").forward(req, resp);
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
