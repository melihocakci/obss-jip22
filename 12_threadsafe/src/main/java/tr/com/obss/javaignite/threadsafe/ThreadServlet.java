package tr.com.obss.javaignite.threadsafe;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class ThreadServlet extends HttpServlet {
    private int money;

    public void init() {
        money = 400;
    }

    public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try{
            int amount = Integer.parseInt(request.getParameter("amount"));

            money -= amount;

            PrintWriter out = response.getWriter();
            out.println("money left: " + money);

            Thread.sleep(3000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}