package tr.com.obss.javaignite.youngorold;

import java.util.Scanner;

public class YoungOrOld {
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
