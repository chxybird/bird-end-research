package com.bird.design.strategy;

/**
 * @Author lipu
 * @Date 2021/6/18 9:55
 * @Description 减法
 */
public class Subtraction implements Strategy{
    @Override
    public int operation(int x, int y) {
        return x-y;
    }
}
