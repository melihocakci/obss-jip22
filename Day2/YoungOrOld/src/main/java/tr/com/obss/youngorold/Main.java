package tr.com.obss.youngorold;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter your age");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextInt() < 50) {
            System.out.println("You are young!");
        } else {
            System.out.println("You are old!");
        }
    }
}
