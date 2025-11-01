package com.zjgsu.obl.course.Course.respository;

import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.model.Enrollment;
import com.zjgsu.obl.course.Course.model.EnrollmentStatus;
import com.zjgsu.obl.course.Course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {

    // 按课程查询
    List<Enrollment> findByCourse(Course course);

    // 按学生查询
    List<Enrollment> findByStudent(Student student);

    // 按状态查询
    List<Enrollment> findByStatus(EnrollmentStatus status);

    // 按课程和学生组合查询
    Optional<Enrollment> findByCourseAndStudent(Course course, Student student);

    // 统计课程的活跃选课人数
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course = :course AND e.status = 'ACTIVE'")
    long countActiveEnrollmentsByCourse(@Param("course") Course course);

    // 检查学生是否已选某课程
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Enrollment e WHERE e.student = :student AND e.course = :course AND e.status = 'ACTIVE'")
    boolean existsByStudentAndCourseAndActive(@Param("student") Student student, @Param("course") Course course);

    // 按学生ID和课程代码查询（用于API兼容）
    @Query("SELECT e FROM Enrollment e WHERE e.student.studentId = :studentId AND e.course.code = :courseCode")
    Optional<Enrollment> findByStudentIdAndCourseCode(@Param("studentId") String studentId, @Param("courseCode") String courseCode);

    // 按课程代码查询选课记录
    @Query("SELECT e FROM Enrollment e WHERE e.course.code = :courseCode")
    List<Enrollment> findByCourseCode(@Param("courseCode") String courseCode);

    // 按学生学号查询选课记录
    @Query("SELECT e FROM Enrollment e WHERE e.student.studentId = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") String studentId);

    // 统计学生选课数量
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.student.studentId = :studentId")
    long countByStudentId(@Param("studentId") String studentId);
}
