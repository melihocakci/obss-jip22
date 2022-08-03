package tr.com.obss.javaignite.wordcounter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordCounter {

    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        Path file = Path.of("C:\\Users\\melih\\repos\\java-ignite-2022\\Day5\\WordCounter\\news.txt");

        try {
            String str = Files.readString(file);
            String[] words = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

            for(String i : words) {
                incrementWord(treeMap, i);
            }

            printMap(treeMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void incrementWord(Map<String, Integer> map, String word) {
        Integer num = map.get(word);
        if (num == null) {
            map.put(word, 1);
        } else {
            map.put(word, num + 1);
        }
    }

    public static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }
}
