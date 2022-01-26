import com.bird.MybatisPlusApp;
import com.bird.entity.Student;
import com.bird.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author lipu
 * @Date 2021/6/18 8:48
 * @Description
 */
@SpringBootTest(classes = MybatisPlusApp.class)
public class MybatisServiceTest {
    @Resource
    private StudentService studentService;

    @Test
    void test1() {
        List<Long> idList=new ArrayList<>();
        idList.add(1L);
        idList.add(2L);
        idList.add(3L);
        idList.add(4L);
        Collection<Student> studentList = studentService.listByIds(idList);
        Student student=new Student();
        student.setName("小鸟");
        student.setAge(24);
        student.setSex("男");
        student.setChineseGrade(110);
        student.setMathGrade(120);
        student.setEnglishGrade(110);
        studentList.add(student);
        //根据主键 如果数据库中有就更新 没有就插入
        studentService.saveOrUpdateBatch(studentList);
    }

    @Test
    void test() {
        List<Student> studentList=new ArrayList<>();
        Student student=new Student();
        student.setId(1L);
        student.setName("yyyy");
        studentList.add(student);
        studentService.saveOrUpdateBatch(studentList);
    }
}
