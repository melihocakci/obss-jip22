package tr.com.obss.javaignite;

public class Employee {
    private final int id;
    private final String name;
    private final String surname;
    private final String title;
    private final int birthYear;

    public Employee(int id, String name, String surname, String title, int birthYear) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", title='" + title + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
