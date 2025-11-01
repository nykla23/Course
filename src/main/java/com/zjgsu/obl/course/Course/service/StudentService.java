package com.zjgsu.obl.course.Course.service;

import com.zjgsu.obl.course.Course.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(String id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        if(!isValidEmail(student.getEmail())){
            throw new IllegalArgumentException("Invalid email format");
        }

        Optional<Student> existStudent = studentRepository.findByStudentId(student.getStudentId());
        if (existStudent.isPresent()){
            throw new IllegalArgumentException("学号已存在");
        }

        Optional<Student> existEmail = studentRepository.findByEmail(student.getEmail());
        if (existEmail.isPresent()){
            throw new IllegalArgumentException("邮箱已存在");
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(String id, Student student) {
        Optional<Student> existStudent = studentRepository.findById(id);
        if (existStudent.isEmpty()){
            return null;
        }

        if(!isValidEmail(student.getEmail())){
            throw new IllegalArgumentException("邮箱格式不正确");
        }

        Optional<Student> studentByStudentId = studentRepository.findByStudentId(student.getStudentId());
        if (studentByStudentId.isPresent() &&!studentByStudentId.get().getId().equals(id)){
            throw new IllegalArgumentException("学号已存在");
        }

        Optional<Student> studentByEmail = studentRepository.findByEmail(student.getEmail());
        if (studentByEmail.isPresent() &&!studentByEmail.get().getId().equals(id)){
            throw new IllegalArgumentException("邮箱已被其他学生使用");
        }

        student.setId(id);
        return studentRepository.save(student);
    }

    public boolean deleteStudent(String id) {
        Optional<Student> existStudent = studentRepository.findById(id);
        if (existStudent.isPresent()){
            studentRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    private boolean isValidEmail(String email){
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
