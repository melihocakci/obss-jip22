package tr.com.obss.thepen2;

public class Main {
    public static void main(String[] args) {
        Pen pen = new Pen();

        Shape circle = new Circle("blue", 2);
        Shape rectangle = new Rectangle("red", 2, 3);

        pen.drawShape(circle);
        pen.drawShape(rectangle);

        System.out.println(circle);
        pen.changeColor(circle, "orange");
        System.out.println(circle);
    }
}
