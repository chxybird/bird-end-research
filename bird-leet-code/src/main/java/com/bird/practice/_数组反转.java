package com.bird.practice;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Author lipu
 * @Date 2021/6/21 15:32
 * @Description
 */
public class _数组反转 {

    public static void reverse(int[] array){
        int temp;
        int low=0;
        int high=array.length-1;
        while (low<=high){
            temp=array[low];
            array[low]=array[high];
            array[high]=temp;
            low++;
            high--;
        }
    }


    public static void main(String[] args) {
        int[] array = new int[]{0,1,2,3,4,5,6,7,8,9,10};
        _数组反转.reverse(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
