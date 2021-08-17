package com.bird.sort;

/**
 * @Author lipu
 * @Date 2021/6/15 11:57
 * @Description 插入排序
 */
public class InsertSort {

    /**
     * @Author lipu
     * @Date 2021/6/15 11:57
     * @Description 排序实现
     */
    public static void sort(int[] array) {
        int index;
        int guard;
        for (int i = 1; i < array.length; i++) {
            guard = array[i];
            index = i;
            for (int j = i; j > 0; j--) {
                if (guard < array[j - 1]) {
                    array[j] = array[j - 1];
                    index = j - 1;
                } else {
                    break;
                }
            }
            array[index] = guard;
        }
    }
}
