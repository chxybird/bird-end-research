package com.bird.mapred.demo2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/6/22 16:53
 * @Description
 */
public class WordCountReducer extends Reducer<Text, IntWritable,Text, IntWritable> {
    private final IntWritable OUT_VALUE = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        //value的值就是map阶段输出的相同KEY值的集合
        for (IntWritable value : values) {
            sum=sum+value.get();
        }
        //写出
        OUT_VALUE.set(sum);
        context.write(key,OUT_VALUE);
    }
}
