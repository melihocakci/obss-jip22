package tr.com.obss.javaignite.employeemanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelationalModel {
    private Connection conn;

    public RelationalModel(String connectionString, String username, String password) {
        try {
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void insertEmployee(Employee employee) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Employees VALUES(?, ?, ?, ?, ?)");
            st.setInt(1, employee.getId());
            st.setString(2, employee.getName());
            st.setString(3, employee.getSurname());
            st.setString(4, employee.getTitle());
            st.setInt(5, employee.getBirthYear());

            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Employee> getEmployees(OrderField of) {
        String order = null;

        switch (of) {
            case NAME:
                order = "name";
            case SURNAME:
                order = "surname";
            case BIRTHYEAR:
                order = "birthyear";
        }

        ArrayList<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM employees ORDER BY ?");
            st.setString(1, order);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String title = rs.getString("title");
                int birthYear = rs.getInt("birthyear");
                employees.add(new Employee(id, name, surname, title, birthYear));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return employees;
    }
}
