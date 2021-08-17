package com.bird.design.strategy;

/**
 * @Author lipu
 * @Date 2021/6/18 9:58
 * @Description
 * 策略模式(Strategy Pattern)中,一个类的行为或其算法可以在运行时更改。策略模式属于对象的行为模式。其用意是针对一组算法,
 * 将每一个算法封装到具有共同接口的独立的类中,从而使得它们可以相互替换。策略模式使得算法可以在不影响到客户端的情况下发生变化。
 * JAVA中的Comparator就使用了策略的设计模式。根据传入不同的Comparator,得到不同的排序结果。
 */
public class Client {
    public static void main(String[] args) {
        Addition addition=new Addition();
        Subtraction subtraction=new Subtraction();

        Context context=new Context(addition);
        context.setStrategy(subtraction);
        int resultOne = context.operation(10, 5);
        int resultTwo = context.operation(10, 5);
        System.out.println(resultOne);
        System.out.println(resultTwo);
    }
}
