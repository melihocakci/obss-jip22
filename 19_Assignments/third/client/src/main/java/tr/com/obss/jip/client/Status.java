package tr.com.obss.jip.client;

public enum Status {
    CORRECT(0),
    SMALLER(1),
    BIGGER(2),
    FAIL(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
