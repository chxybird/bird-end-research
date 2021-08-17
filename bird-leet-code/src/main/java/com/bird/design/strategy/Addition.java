package com.bird.design.strategy;

/**
 * @Author lipu
 * @Date 2021/6/18 9:54
 * @Description 加法
 */
public class Addition implements Strategy{
    @Override
    public int operation(int x, int y) {
        return x+y;
    }
}
