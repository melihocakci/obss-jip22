package tr.com.obss.jip;

public enum Status {
    CORRECT(0),
    SMALLER(1),
    BIGGER(2),
    FAIL(3);

    public final int value;

    Status(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
