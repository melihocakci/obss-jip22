package tr.com.obss.javaignite;

import java.util.ArrayList;

public class Prog2 {
    public static void main(String[] args) {
        RelationalModel rm = new RelationalModel(
                "jdbc:postgresql://localhost:5432/employeemanagement", "postgres", "727");

        ArrayList<Employee> employees = (ArrayList<Employee>) rm.getEmployees(OrderField.SURNAME);
        for (Employee employee : employees) {
            System.out.println(employee.getName() + ' ' + employee.getSurname() + ' ' + employee.getTitle());
        }
    }
}
