package tr.com.obss.thepen2;

public class Pen {
    public void drawShape(Shape shape) {
        System.out.println(shape.draw());
    }

    public void changeColor(Shape shape, String color) {
        shape.setColor(color);
    }
}
