package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findByEmail(String email);

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);

    List<Student> findByMajor(String major);

    List<Student> findByGrade(Integer grade);

    List<Student> findeByMajorAndGrade(String major, Integer grade);
}
