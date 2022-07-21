package tr.com.obss.thepen2;

public abstract class Shape {
    String color;

    public Shape(String color) {
        this.color = color;
    }

    public abstract double draw();

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
