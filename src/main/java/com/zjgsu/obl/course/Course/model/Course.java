package com.zjgsu.obl.course.Course.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id; // 系统生成的课程ID

    @Column(name = "course_code", unique = true,nullable = false,length = 50)
    private String code; // 课程代码，如 "CS101"

    @Column(nullable = false)
    private String title; // 课程名称

    @Embedded
    private Instructor instructor; // 授课教师

    @Embedded
    private ScheduleSlot schedule; // 课程安排

    @Column(nullable = false)
    private Integer capacity = 0; // 课程容量

    @Column(name = "enrolled_count")
    private Integer enrolled = 0; // 已选课人数，初始为0

    public Course() {
        this.enrolled = 0;
    }
    public Course(String id, String code, String title, Instructor instructor, ScheduleSlot schedule, Integer capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.schedule = schedule;
        this.capacity = capacity;
        this.enrolled = 0;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    public ScheduleSlot getSchedule() {
        return schedule;
    }
    public void setSchedule(ScheduleSlot schedule) {
        this.schedule = schedule;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }
}
