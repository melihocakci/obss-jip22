package tr.com.obss.jip.second;

import java.io.Serializable;

public class Bean implements Serializable {
    private String name;
    private double price;
    private int time;

    public Bean() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
