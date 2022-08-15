package tr.com.obss.jip.bookportal.other;

public enum RoleType {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
