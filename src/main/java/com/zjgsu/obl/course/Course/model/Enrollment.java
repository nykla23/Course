package com.zjgsu.obl.course.Course.model;

import java.time.LocalDateTime;

public class Enrollment {
    private String id;//选课记录ID
    private String studentId;//学生ID
    private String courseId;//课程ID
    private LocalDateTime enrolledAt;//选课时间

    public Enrollment() {
        this.enrolledAt = LocalDateTime.now();
    }

    public Enrollment(String id, String studentId, String courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;//系统在创建课程时自动生成的UUID，并非课程代码
        this.enrolledAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
