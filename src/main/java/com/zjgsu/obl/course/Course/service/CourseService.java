package com.zjgsu.obl.course.Course.service;

import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.model.Instructor;
import com.zjgsu.obl.course.Course.model.ScheduleSlot;
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
        // 添加必填字段验证
        if (course.getCode() == null || course.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("课程代码不能为空");
        }
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("课程名称不能为空");
        }
        if (course.getInstructor() == null) {
            throw new IllegalArgumentException("教师信息不能为空");
        }
        if (course.getSchedule() == null) {
            throw new IllegalArgumentException("课程安排不能为空");
        }
        if (course.getCapacity() == null || course.getCapacity() <= 0) {
            throw new IllegalArgumentException("课程容量必须大于0");
        }

        return courseRepository.save(course);
    }

    public Course updateCourse(String id, Course courseUpdate) {
        // 检查课程是否存在
        Optional<Course> existingCourseOpt = courseRepository.findById(id);
        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();

            // 合并更新逻辑 - 只更新非空的字段
            if (courseUpdate.getCode() != null) {
                existingCourse.setCode(courseUpdate.getCode());
            }
            if (courseUpdate.getTitle() != null) {
                existingCourse.setTitle(courseUpdate.getTitle());
            }
            if (courseUpdate.getInstructor() != null) {
                // 合并更新教师信息
                Instructor existingInstructor = existingCourse.getInstructor();
                Instructor updateInstructor = courseUpdate.getInstructor();

                if (updateInstructor.getId() != null) {
                    existingInstructor.setId(updateInstructor.getId());
                }
                if (updateInstructor.getName() != null) {
                    existingInstructor.setName(updateInstructor.getName());
                }
                if (updateInstructor.getEmail() != null) {
                    existingInstructor.setEmail(updateInstructor.getEmail());
                }
            }
            if (courseUpdate.getSchedule() != null) {
                // 合并更新课程安排
                ScheduleSlot existingSchedule = existingCourse.getSchedule();
                ScheduleSlot updateSchedule = courseUpdate.getSchedule();

                if (updateSchedule.getDayOfWeek() != null) {
                    existingSchedule.setDayOfWeek(updateSchedule.getDayOfWeek());
                }
                if (updateSchedule.getStartTime() != null) {
                    existingSchedule.setStartTime(updateSchedule.getStartTime());
                }
                if (updateSchedule.getEndTime() != null) {
                    existingSchedule.setEndTime(updateSchedule.getEndTime());
                }
                if (updateSchedule.getExpectedAttendance() != null) {
                    existingSchedule.setExpectedAttendance(updateSchedule.getExpectedAttendance());
                }
            }
            if (courseUpdate.getCapacity() != null) {
                existingCourse.setCapacity(courseUpdate.getCapacity());
            }

            return courseRepository.save(existingCourse);  // 保存合并后的对象
        } else {
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
