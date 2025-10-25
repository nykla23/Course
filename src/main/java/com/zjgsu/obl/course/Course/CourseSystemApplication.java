package com.zjgsu.obl.course.Course;

import com.zjgsu.obl.course.Course.model.Course;
import com.zjgsu.obl.course.Course.model.Instructor;
import com.zjgsu.obl.course.Course.model.ScheduleSlot;
import com.zjgsu.obl.course.Course.model.Student;
import com.zjgsu.obl.course.Course.service.CourseService;
import com.zjgsu.obl.course.Course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseSystemApplication implements CommandLineRunner {

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentService studentService;

	public static void main(String[] args) {
		SpringApplication.run(CourseSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 初始化测试数据
		initTestData();
	}

	private void initTestData() {
		// 创建测试课程
		Instructor instructor1 = new Instructor("T001", "张教授", "zhang@example.edu.cn");
		ScheduleSlot schedule1 = new ScheduleSlot("MONDAY", "08:00", "10:00", 50);
		Course course1 = new Course();
		course1.setCode("CS101");
		course1.setTitle("计算机科学导论");
		course1.setInstructor(instructor1);
		course1.setSchedule(schedule1);
		course1.setCapacity(60);
		courseService.createCourse(course1);

		Instructor instructor2 = new Instructor("T002", "李教授", "li@example.edu.cn");
		ScheduleSlot schedule2 = new ScheduleSlot("TUESDAY", "10:00", "12:00", 40);
		Course course2 = new Course();
		course2.setCode("MATH201");
		course2.setTitle("高等数学");
		course2.setInstructor(instructor2);
		course2.setSchedule(schedule2);
		course2.setCapacity(50);
		courseService.createCourse(course2);

		// 创建测试学生
		Student student1 = new Student();
		student1.setStudentId("S2024001");
		student1.setName("张三");
		student1.setMajor("计算机科学与技术");
		student1.setGrade(2024);
		student1.setEmail("zhangsan@example.com");
		studentService.createStudent(student1);

		Student student2 = new Student();
		student2.setStudentId("S2024002");
		student2.setName("李四");
		student2.setMajor("软件工程");
		student2.setGrade(2024);
		student2.setEmail("lisi@example.com");
		studentService.createStudent(student2);

		System.out.println("测试数据初始化完成！");
	}
}
