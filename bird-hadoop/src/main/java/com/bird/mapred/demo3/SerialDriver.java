package com.bird.mapred.demo3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Author lipu
 * @Date 2021/6/29 15:34
 * @Description
 */
public class SerialDriver {
    public static void main(String[] args) throws Exception{
        //获取Job
        Configuration configuration=new Configuration();
        Job job = Job.getInstance(configuration);
        //设置jar包路径
        job.setJarByClass(SerialDriver.class);
        //关联mapper与reducer
        job.setMapperClass(SerialMapper.class);
        job.setReducerClass(SerialReducer.class);
        //设置map输出的kv类型
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Student.class);

        //设置reduce输出kv类型
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Student.class);
        //设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("F:\\BigData\\input\\serial"));
        FileOutputFormat.setOutputPath(job,new Path("F:\\BigData\\output"));
        //提交Job
        boolean result = job.waitForCompletion(true);
        System.out.println(result ? "运行成功" : "运行失败");
        //0正常退出 1非正常退出
        System.exit(result ? 0 : 1);
    }
}
