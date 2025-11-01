package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    // 按课程代码查询课程
    Optional<Course> findByCode(String code);

    // 按讲师ID查询
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(@Param("instructorId") String instructorId);

    // 按标题关键字模糊查询
    List<Course> findByTitleContainingIgnoreCase(String keyword);

    // 查询有剩余容量的课程
    @Query("SELECT c FROM Course c WHERE c.capasity > c.enrolled")
    List<Course> findAvailableCourses();

    // 检查课程代码是否存在
    boolean existsByCode(String code);
}
