package com.zjgsu.obl.course.Course.controller;



import com.zjgsu.obl.course.Course.common.ApiResponse;
import com.zjgsu.obl.course.Course.model.Student;
import com.zjgsu.obl.course.Course.service.EnrollmentService;
import com.zjgsu.obl.course.Course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentService enrollmentService;

    // 查询所有学生
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    // 根据ID查询学生
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable String id) {
        return studentService.findById(id)
                .map(student -> ResponseEntity.ok(ApiResponse.success(student)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Student not found", healthInfo)));
    }

    // 创建学生
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, e.getMessage(), healthInfo));
        }
    }

    // 更新学生信息
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable String id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Student not found", healthInfo));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, e.getMessage(), healthInfo));
        }
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable String id) {
        // 检查学生是否存在
        if (studentService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Student not found", healthInfo));
        }

        // 检查学生是否有选课记录
        if (enrollmentService.hasEnrollments(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "无法删除：该学生存在选课记录", healthInfo));
        }

        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.success("Student deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Student not found", healthInfo));
        }
    }
}