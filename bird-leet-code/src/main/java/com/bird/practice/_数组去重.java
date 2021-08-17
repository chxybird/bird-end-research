package com.bird.practice;

/**
 * @Author lipu
 * @Date 2021/6/21 16:38
 * @Description
 */
public class _数组去重 {

    public static void removeDuplicates(int[] array){
        int fast=0;
        int low=0;
        for (int i = 0; i < array.length-1; i++) {
            fast++;
            if (array[fast]!=array[low]){
                low++;
                array[low]=array[fast];
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{0,1,1,1,1,5,5,7,8,9,10};
        _数组去重.removeDuplicates(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
