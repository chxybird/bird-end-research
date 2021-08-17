package com.bird.sort;

/**
 * @Author lipu
 * @Date 2021/6/18 17:10
 * @Description 排序练习
 */
public class Practice implements Sort {

    @Override
    public void bubbleSort(int[] array) {
        int temp;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public void insertSort(int[] array) {
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

    @Override
    public void quickSort(int[] array) {
        int low = 0;
        int high = array.length - 1;
        quick(array, low, high);
    }

    private void quick(int[] array, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int guard = array[low];
        int temp;
        while (i < j) {
            while (guard <= array[j] && i < j) {
                j--;
            }
            while (guard >= array[i] && i < j) {
                i++;
            }
            if (i < j) {
                temp = array[j];
                array[j] = array[i];
                array[i] = temp;
            }
        }
        temp = array[i];
        array[i] = array[low];
        array[low] = temp;
        quick(array, low, i - 1);
        quick(array, j + 1, high);
    }

    @Override
    public void selectSort(int[] array) {
        int temp;
        int index;
        for (int i = 0; i < array.length; i++) {
            index = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[index]) {
                    index = j;
                }
            }
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

}
