package com.zjgsu.obl.course.Course.service;

import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.respository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(String id, Course course){
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()){
            course.setId(id);
            return courseRepository.save(course);
        }else {
            return null;
        }
    }

    public boolean deleteCourse(String id){
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()){
            courseRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
