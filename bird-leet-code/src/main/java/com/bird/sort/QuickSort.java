package com.bird.sort;

/**
 * @Author lipu
 * @Date 2021/6/18 15:04
 * @Description 快排
 */
public class QuickSort {


    /**
     * @Author lipu
     * @Date 2021/6/18 15:04
     * @Description 排序实现
     */
    public static void sort(int[] array) {
        //初始化 low high
        int low = 0;
        int high = array.length - 1;
        //排序
        divide(array, low, high);
    }

    /**
     * @Author lipu
     * @Date 2021/6/18 15:13
     * @Description 递归体
     */
    private static void divide(int[] array, int low, int high) {
        if (low>=high){
            return;
        }
        //临时变量
        int temp;
        //选择基准 每次都让排序数组的第一个元素作为基准
        int guard = array[low];
        int i=low;
        int j=high;
        while (i<j){
            //从右往左找到一个小于基准的数
            while (array[j] >= guard && i < j) {
                j--;
            }
            //从左往右找到一个大于基准数
            while (array[i] <= guard && i < j) {
                i++;
            }
            //交换位置
            if (i < j) {
                temp=array[i];
                array[i]=array[j];
                array[j]=temp;
            }
        }
        //替换基准 low与high现在相遇
        temp=array[i];
        array[i]=array[low];
        array[low]=temp;
        //左右递归
        divide(array,low,i-1);
        divide(array,i+1,high);
    }
}
