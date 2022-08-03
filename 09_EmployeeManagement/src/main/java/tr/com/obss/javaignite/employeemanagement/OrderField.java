package tr.com.obss.javaignite.employeemanagement;

public enum OrderField {
    NAME("name"),
    SURNAME("surname"),
    BIRTHYEAR("birthyear");

    private final String string;

    OrderField(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
