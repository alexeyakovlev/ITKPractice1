import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexi on 22.12.2025
 */
public class CountOfElements {
    public static  <T> Map<T, Integer> arrayToMap(T[] array) {
        Map<T, Integer> map = new HashMap<>();

        for (T element : array) {
            map.put(element, map.getOrDefault(element, 0) + 1);
        }
        return map;
    }

    public static void main(String[] args) {
        String[] words = {"apple", "banana", "apple", "banana", "orange"};
        System.out.println(arrayToMap(words));
    }
}