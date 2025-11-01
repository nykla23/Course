package com.zjgsu.obl.course.Course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSystemApplication.class, args);
	}

	// 移除 CommandLineRunner 和 initTestData 方法
	// 数据初始化现在由 schema.sql 和 data.sql 处理
}