package com.bird.mapred.demo3;

import lombok.Data;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Author lipu
 * @Date 2021/6/25 17:41
 * @Description Hadoop序列化
 * 实现Writable接口重写序列化与反序列化方法(顺序要一致)
 * 提供空参的构造方法
 * 重写toString方法
 */
@Data
public class Student implements Writable {
    private Integer id;
    private String name;
    private Integer age;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer sum;

    /**
     * @Author lipu
     * @Date 2021/6/25 17:56
     * @Description 序列化
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeChars(name);
        dataOutput.writeInt(age);
        dataOutput.writeInt(chinese);
        dataOutput.writeInt(math);
        dataOutput.writeInt(english);
        dataOutput.writeInt(sum);
    }

    /**
     * @Author lipu
     * @Date 2021/6/25 17:56
     * @Description 反序列化
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.name = dataInput.readLine();
        this.age = dataInput.readInt();
        this.chinese = dataInput.readInt();
        this.math = dataInput.readInt();
        this.english = dataInput.readInt();
        this.sum = dataInput.readInt();
    }



    @Override
    public String toString() {
        return id + "|" + name + "|" + age + "|" + chinese + "|" + math + "|" + english + "|" + sum;
    }
}