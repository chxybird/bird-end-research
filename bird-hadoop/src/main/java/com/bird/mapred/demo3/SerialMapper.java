package com.bird.mapred.demo3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/6/25 20:31
 * @Description
 */
public class SerialMapper extends Mapper<LongWritable, Text, IntWritable, Student> {
    private final IntWritable OUT_KEY = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行数据
        String line = value.toString();
        //切割字符串
        String[] elements = line.split("\\|");
        Student student = new Student();
        int id = Integer.parseInt(elements[0]);
        String name = elements[1];
        Integer age = Integer.parseInt(elements[2]);
        Integer chinese = Integer.parseInt(elements[3]);
        Integer math = Integer.parseInt(elements[4]);
        Integer english = Integer.parseInt(elements[5]);
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setChinese(chinese);
        student.setEnglish(english);
        student.setMath(math);
        student.setSum(chinese + english + math);

        OUT_KEY.set(id);
        context.write(OUT_KEY, student);


    }
}
