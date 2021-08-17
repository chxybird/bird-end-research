package com.bird.mapred.demo1;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/6/22 16:53
 * @Description
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final Text OUT_KEY = new Text();
    private final IntWritable OUT_VALUE = new IntWritable(1);

    /**
     * @Author lipu
     * @Date 2021/6/22 17:25
     * @Description 实现自定义map逻辑
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行数据
        String line = value.toString();
        //切割
        String[] words = line.split("\\|");
        //遍历写出给reduce
        for (String word : words) {
            OUT_KEY.set(word);
            context.write(OUT_KEY, OUT_VALUE);
        }
    }
}
