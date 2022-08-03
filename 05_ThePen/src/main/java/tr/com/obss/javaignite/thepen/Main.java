package tr.com.obss.javaignite.thepen;

public class Main {
    public static void main(String[] args) {
        Pen pen = new Pen();

        Circle c = new Circle(2, "red");
        Rectangle r = new Rectangle(2, 2, "blue");

        System.out.print("Area of rectangle: ");
        pen.drawRectangle(r);

        System.out.print("Area of circle: ");
        pen.drawCircle(c);

        pen.changeColorRectangle("yellow", r);
        pen.changeColorCircle("purple", c);
    }
}
