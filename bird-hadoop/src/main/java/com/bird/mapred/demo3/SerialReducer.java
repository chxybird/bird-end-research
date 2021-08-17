package com.bird.mapred.demo3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/6/29 14:38
 * @Description
 */
public class SerialReducer extends Reducer<IntWritable, Student, IntWritable, Student> {
    private final Student OUT_VALUE = new Student();

    @Override
    protected void reduce(IntWritable key, Iterable<Student> values, Context context) throws IOException, InterruptedException {
        int totalSum = 0;
        for (Student student : values) {
            Integer sum = student.getSum();
            totalSum = totalSum + sum;
        }

        OUT_VALUE.setSum(totalSum);
        context.write(key, OUT_VALUE);
    }
}
