package tr.com.obss.javaignite.threadsafe;

public class Contact {
    private int id;
    private String name;
    private String tel_number;

    public Contact(int id, String name, String tel_number) {
        this.id = id;
        this.name = name;
        this.tel_number = tel_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel_number() {
        return tel_number;
    }
}
