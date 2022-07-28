package tr.com.obss.javaignite.averagegrade;

import java.util.Scanner;

public class AverageGrade {
    public static void main(String[] args) {
        int counter = 0;
        double sum = 0, grade;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Enter grade");
            grade = scanner.nextDouble();

            sum += grade;
            counter++;
        } while (grade != 101);

        sum -= 101;
        counter--;

        System.out.println("Average grade: " + sum / counter);
    }
}
