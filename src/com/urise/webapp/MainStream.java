package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 3, 2, 3};
        System.out.println(minValue(array));
        List<Integer> integers = Arrays.stream(array).boxed().collect(Collectors.toList());
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        int[] list = Arrays.stream(values).distinct().sorted().toArray();
        return IntStream.range(0, list.length)
                .map(i -> (int) (Math.pow(10, list.length - 1 - i) * list[i]))
                .sum();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(
                        integers.stream().mapToInt(Integer::intValue)
                                .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0)
                .collect(Collectors.toList());
    }
}
