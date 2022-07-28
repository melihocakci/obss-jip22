package tr.com.obss.javaignite.employeemanagement;

public class Prog1 {
    public static void main(String[] args) {
        RelationalModel rm = new RelationalModel(
                "jdbc:postgresql://localhost:5432/employeemanagement", "postgres", "727");

        Employee[] employees = new Employee[10];
        employees[0] = new Employee(1, "Adnan", "Ari", "CEO", 1998);
        employees[1] = new Employee(2, "Canan", "Sari", "Hademe", 1990);
        employees[2] = new Employee(3, "Murat", "Bucan", "Mudur", 2000);
        employees[3] = new Employee(4, "Eren", "Atmaca", "Yazilim Muh.", 2002);
        employees[4] = new Employee(5, "Cengiz", "Karaoglu", "Stajyer", 1987);
        employees[5] = new Employee(6, "Enes", "Karaoglu", "Yazilim Muh.", 1995);
        employees[6] = new Employee(7, "Onur", "Demir", "Mudur", 1994);
        employees[7] = new Employee(8, "Salih", "Sari", "Baskan Yardimcisi", 1985);
        employees[8] = new Employee(9, "Melahat", "Demir", "Mudur", 1990);
        employees[9] = new Employee(10, "Eray", "Demir", "Mudur", 1989);

        for (Employee employee : employees) {
            rm.insertEmployee(employee);
        }
    }
}
