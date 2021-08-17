package com.bird.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird.dao.StudentDao;
import com.bird.entity.Student;
import com.bird.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @Author lipu
 * @Date 2021/6/18 8:50
 * @Description
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
}
