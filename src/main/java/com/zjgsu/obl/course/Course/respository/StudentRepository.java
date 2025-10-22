package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepository {
    private final Map<String, Student> students = new ConcurrentHashMap<>();

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id));
    }

    public Student save(Student student) {
        // 如果 student 的 id 为 null，则生成一个 UUID
        if (student.getId() == null) {
            String id = java.util.UUID.randomUUID().toString();
            student.setId(id);
        }
        students.put(student.getId(), student);
        return student;
    }

    public void deleteById(String id) {
        students.remove(id);
    }

    // 根据学号查询学生（用于验证学号是否重复）
    public Optional<Student> findByStudentId(String studentId) {
        return students.values().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    }

    // 根据邮箱查询学生（用于验证邮箱是否重复）
    public Optional<Student> findByEmail(String email) {
        return students.values().stream()
                .filter(student -> student.getEmail().equals(email))
                .findFirst();
    }
}