package tr.com.obss.reversearray;

import java.util.Random;

public class ReverseArray {
    public static void main(String[] args) {
        int[] array = new int[10];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(20);
        }

        System.out.println("First array:");
        printArray(array);

        int[] reversedArray = reverseArray(array);

        System.out.println("Reversed array:");
        printArray(reversedArray);
    }

    private static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static int[] reverseArray(int[] array) {
        int[] reversedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[array.length - 1 - i] = array[i];
        }

        return reversedArray;
    }
}
