package tr.com.obss.pen;

public class Main {
    public static void main(String[] args) {
        Pen pen = new Pen();

        Circle c = new Circle(2, "red");
        Rectangle r = new Rectangle(2, 2, "blue");

        pen.drawRectangle(r);
        pen.drawCircle(c);

        pen.changeColorRectangle("yellow", r);
        pen.changeColorCircle("purple", c);
    }
}
