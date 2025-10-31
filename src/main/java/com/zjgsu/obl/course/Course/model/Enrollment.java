package com.zjgsu.obl.course.Course.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    // 为了保持API兼容性，添加这些 transient 字段
    // 这些字段不会保存到数据库，但会在JSON响应中返回
    @Transient
    private String studentId;

    @Transient
    private String courseId;

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
    }

    // 原有的构造方法保持不变
    public Enrollment() {
        this.enrolledAt = LocalDateTime.now();
    }

    public Enrollment(String id, String studentId, String courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = LocalDateTime.now();
    }

    // getter/setter 保持原有逻辑
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) {
        this.student = student;
        // 自动设置 studentId 用于API响应
        if (student != null) {
            this.studentId = student.getStudentId();
        }
    }

    public Course getCourse() { return course; }
    public void setCourse(Course course) {
        this.course = course;
        // 自动设置 courseId 用于API响应
        if (course != null) {
            this.courseId = course.getCode();
        }
    }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    // 为了保持API兼容性，保留这些getter方法
    public String getStudentId() {
        return studentId != null ? studentId : (student != null ? student.getStudentId() : null);
    }

    public String getCourseId() {
        return courseId != null ? courseId : (course != null ? course.getCode() : null);
    }

    // 也提供setter用于反序列化
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
}