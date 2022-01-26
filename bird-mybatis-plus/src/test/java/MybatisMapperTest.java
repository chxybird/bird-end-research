import com.bird.MybatisPlusApp;
import com.bird.dao.StudentDao;
import com.bird.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author lipu
 * @Date 2021/11/17 10:22
 * @Description
 */
@SpringBootTest(classes = MybatisPlusApp.class)
public class MybatisMapperTest {
    @Resource
    private StudentDao studentDao;

    @Test
    void test() {
        Student student=new Student();
        student.setId(1L);
        student.setName("xxxx");
        studentDao.updateById(student);
    }
}
