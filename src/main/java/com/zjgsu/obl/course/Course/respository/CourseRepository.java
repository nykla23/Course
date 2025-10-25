package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courses.get(id));
    }//这里是系统自动生成的UUID

    public Course save(Course course) {
        // 如果 course 的 id 为 null，则生成一个 UUID
        if (course.getId() == null) {
            String id = java.util.UUID.randomUUID().toString();
            course.setId(id);
        }
        courses.put(course.getId(), course);
        return course;
    }

    public void deleteById(String id) {
        courses.remove(id);
    }

    // 根据课程代码查询课程（用于验证课程代码是否重复）
    public Optional<Course> findByCode(String code) {
        return courses.values().stream()
                .filter(course -> course.getCode().equals(code))
                .findFirst();
    }
}