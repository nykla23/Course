package com.zjgsu.obl.course.Course.service;

import com.zjgsu.obl.course.Course.exception.ResourceNotFoundException;
import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.model.Enrollment;
import com.zjgsu.obl.course.Course.model.EnrollmentStatus;
import com.zjgsu.obl.course.Course.model.Student;
import com.zjgsu.obl.course.Course.respository.CourseRepository;
import com.zjgsu.obl.course.Course.respository.EnrollmentRepository;
import com.zjgsu.obl.course.Course.respository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

        // 根据 studentId 字符串查找 Student 实体
        Optional<Student> student = studentRepository.findByStudentId(enrollment.getStudentId());
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("学生不存在");
        }

        // 根据 courseId 字符串查找 Course 实体
        Optional<Course> course = courseRepository.findById(enrollment.getCourseId());
        if (course.isEmpty()) {
            throw new ResourceNotFoundException("课程不存在");
        }

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByCourseAndStudent(course.get(),student.get());
        if (existingEnrollment.isPresent()) {
            throw new IllegalArgumentException("该学生已经选过这门课程");
        }

        Course courseObj = course.get();
        long currentEnrollments = enrollmentRepository.countActiveEnrollmentsByCourse(courseObj);
        if (currentEnrollments >= courseObj.getCapacity()) {
            throw new IllegalArgumentException("课程容量已满");
        }
        // 设置对象关联
        enrollment.setStudent(student.get());
        enrollment.setCourse(courseObj);

        // 保存选课记录
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // 更新课程的已选人数
        courseObj.setEnrolled((int) currentEnrollments + 1);
        courseRepository.save(courseObj);

        return savedEnrollment;
    }

    public boolean deleteEnrollment(String id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            Enrollment enrollmentObj = enrollment.get();
            Course course = enrollmentObj.getCourse();

            //删除选课记录
            enrollmentRepository.deleteById(id);

            // 更新课程的已选人数
            long currentEnrollments = enrollmentRepository.countActiveEnrollmentsByCourse(course);
            course.setEnrolled((int) currentEnrollments);
            courseRepository.save(course);

            return true;
        } else {
            return false;
        }
    }

    // 根据课程ID查询选课记录
    public List<Enrollment> findByCourseId(String courseCode) {
        return enrollmentRepository.findByCourseCode(courseCode);
    }

    // 根据学生ID查询选课记录
    public List<Enrollment> findByStudentId(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // 检查学生是否有选课记录
    public boolean hasEnrollments(String studentId) {
        return enrollmentRepository.countByStudentId(studentId) > 0;
    }

    public List<Enrollment> findByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status);
    }

    public long countActiveEnrollmentsByCourse(String courseCode) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        return course.map(c -> enrollmentRepository.countActiveEnrollmentsByCourse(c)).orElse(0L);
    }

    public boolean hasActiveEnrollment(String studentId, String courseCode) {
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        Optional<Course> course = courseRepository.findByCode(courseCode);

        if (student.isPresent() && course.isPresent()) {
            return enrollmentRepository.existsByStudentAndCourseAndActive(student.get(), course.get());
        }
        return false;
    }

    public List<Enrollment> findByMultipleCriteria(String courseCode, String studentId, EnrollmentStatus status) {
        return enrollmentRepository.findByMultipleCriteria(courseCode, studentId, status);
    }
}