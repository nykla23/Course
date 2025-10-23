package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository {
    private final Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    public Optional<Enrollment> findById(String id) {
        return Optional.ofNullable(enrollments.get(id));
    }

    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null){
            String id = java.util.UUID.randomUUID().toString();
            enrollment.setId(id);
        }
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    public void deleteById(String id) {

            enrollments.remove(id);
    }

    public Optional<Enrollment> findByStudentIdAndCourseId(String studentId, String courseId) {
        return enrollments.values().stream()
               .filter(e -> e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId))
               .findFirst();
    }

    public List<Enrollment> findByCourseId(String courseId) {
        return enrollments.values().stream()
               .filter(e -> e.getCourseId().equals(courseId))
               .toList();
    }

    public List<Enrollment> findByStudentId(String studentId) {
        return enrollments.values().stream()
               .filter(e -> e.getStudentId().equals(studentId))
               .collect(Collectors.toList());
    }

    public long countByStudentId(String studentId) {
        return enrollments.values().stream()
               .filter(e -> e.getStudentId().equals(studentId))
               .count();
    }

    public Optional<Enrollment> findByCourseIdAndStudentId(String courseId, String studentId) {
        return enrollments.values().stream()
               .filter(e -> e.getCourseId().equals(courseId) && e.getStudentId().equals(studentId))
               .findFirst();
    }
}
