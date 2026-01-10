package javaOne.javaCollections;

/**
 * Created by alexi on 07.01.2026
 */

public class Task1 {

    public interface Filter<T> {
        T apply(T o);
    }

    public static <T> T[] filter(T[] array, Filter<T> filter) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }

        Object[] result = new Object[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = filter.apply(array[i]);
        }

        return (T[]) result;
    }

    public static void main(String[] args) {
        String[] strings = {"hello", "world", "java"};

        Filter<String> toUpperCaseFilter = new Filter<String>() {
            @Override
            public String apply(String o) {
                return o.toUpperCase();
            }
        };

        String[] result1 = filter(strings, toUpperCaseFilter);
        System.out.println("Результат 1:");
        for (String s : result1) {
            System.out.println(s);
        }
    }
}