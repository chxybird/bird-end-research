package com.bird.sort;

import java.util.Arrays;

/**
 * @Author lipu
 * @Date 2021/6/15 11:15
 * @Description
 */
public class Client {
    public static void main(String[] args) {
        int[] array = new int[]{10, 5, 25, 4, 1, 56, 48, 99, 2, 12};
        Sort sort = new Practice();
//        SelectionSort.sort(array);
//        sort.selectSort(array);
//        sort.insertSort(array);
        sort.quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
