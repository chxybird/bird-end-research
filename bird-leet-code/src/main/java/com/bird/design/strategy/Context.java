package com.bird.design.strategy;

import lombok.Data;

/**
 * @Author lipu
 * @Date 2021/6/18 9:58
 * @Description
 */
@Data
public class Context implements Strategy{
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy=strategy;
    }

    @Override
    public int operation(int x, int y) {
        return strategy.operation(x,y);
    }



}
