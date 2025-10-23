package com.zjgsu.obl.course.Course.service;



import com.zjgsu.obl.course.Course.exception.ResourceNotFoundException;
import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.model.Enrollment;
import com.zjgsu.obl.course.Course.model.Student;
import com.zjgsu.obl.course.Course.respository.CourseRepository;
import com.zjgsu.obl.course.Course.respository.EnrollmentRepository;
import com.zjgsu.obl.course.Course.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(String id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        // 检查课程是否存在
        Optional<Course> course = courseRepository.findById(enrollment.getCourseId()); //这里的findById是课程代码
        if (course.isEmpty()) {
            throw new ResourceNotFoundException("课程不存在");
        }

        // 检查学生是否存在
        Optional<Student> student = studentRepository.findByStudentId(enrollment.getStudentId());
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("学生不存在");
        }

        // 检查是否已经选过该课程
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByCourseIdAndStudentId(
                enrollment.getCourseId(), student.get().getId()); //这里使用学生的UUID

        System.out.println("重复选课检查:");
        System.out.println("课程ID: " + enrollment.getCourseId());
        System.out.println("学生UUID: " + student.get().getId());
        System.out.println("是否已存在选课记录: " + existingEnrollment.isPresent());

        if (existingEnrollment.isPresent()) {
            throw new IllegalArgumentException("该学生已经选过这门课程");
        }

        // 检查课程容量
        Course courseObj = course.get();
        if (courseObj.getEnrolled() >= courseObj.getCapacity()) {
            throw new IllegalArgumentException("课程容量已满");
        }

        // 设置 enrollment 的学生ID为学生的UUID（不是学号）
        enrollment.setStudentId(student.get().getId());

        // 创建选课记录
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 更新课程的已选人数
        courseObj.setEnrolled(courseObj.getEnrolled() + 1);
        courseRepository.save(courseObj);

        return savedEnrollment;
    }

    public boolean deleteEnrollment(String id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            // 删除选课记录
            enrollmentRepository.deleteById(id);

            // 更新课程的已选人数
            String courseId = enrollment.get().getCourseId();
            Optional<Course> course = courseRepository.findById(courseId);
            if (course.isPresent()) {
                Course courseObj = course.get();
                courseObj.setEnrolled(courseObj.getEnrolled() - 1);
                courseRepository.save(courseObj);
            }

            return true;
        } else {
            return false;
        }
    }

    // 根据课程ID查询选课记录
    public List<Enrollment> findByCourseId(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    // 根据学生ID查询选课记录
    public List<Enrollment> findByStudentId(String studentId) {
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if (student.isPresent()){
            return enrollmentRepository.findByStudentId(student.get().getId());
        }else {
            return new ArrayList<>();
        }
    }

    // 检查学生是否有选课记录
    public boolean hasEnrollments(String studentId) {
        return enrollmentRepository.countByStudentId(studentId) > 0;
    }
}