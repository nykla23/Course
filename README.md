# **课程选课系统 - README**

## 项目概述

这是一个基于Spring Boot的课程选课管理系统，支持学生选课、课程管理、教师管理等功能。系统采用内存存储（ConcurrentHashMap）实现数据持久化。

## 功能特性

学生管理

### 学生信息注册与维护

学号唯一性验证

邮箱格式验证

删除前检查选课记录

### 课程管理

课程信息管理（课程代码、名称、教师、时间安排）

课程容量控制

自动生成课程ID

### 选课系统

学生选课功能

退选功能

课程容量检查

防止重复选课

选课记录查询（按学生/按课程）

### 教师管理

教师信息管理

课程与教师关联

## 技术栈

**后端框架**: Spring Boot

**数据存储**: 内存存储（ConcurrentHashMap）

**API风格**: RESTful

**异常处理**: 全局异常处理机制

**响应格式**: 统一API响应格式

## 项目结构

* src/main/java/com/zjgsu/obl/course/Course/
* ├── common/
* │   └── ApiResponse.java          # 统一API响应格式
* ├── controller/
* │   ├── CourseController.java     # 课程控制器
* │   ├── EnrollmentController.java # 选课控制器
* │   └── StudentController.java    # 学生控制器
* ├── exception/
* │   ├── GlobalExceptionHandler.java    # 全局异常处理
* │   └── ResourceNotFoundException.java # 资源未找到异常
* ├── model/
* │   ├── Course.java               # 课程实体
* │   ├── Enrollment.java           # 选课记录实体
* │   ├── Instructor.java           # 教师实体
* │   ├── ScheduleSlot.java         # 课程时间安排
* │   └── Student.java              # 学生实体
* ├── repository/
* │   ├── CourseRepository.java     # 课程数据访问层
* │   ├── EnrollmentRepository.java # 选课数据访问层
* │   └── StudentRepository.java    # 学生数据访问层
* ├── service/
* │   ├── CourseService.java        # 课程业务逻辑
* │   ├── EnrollmentService.java    # 选课业务逻辑
* │   └── StudentService.java       # 学生业务逻辑
* └── CourseSystemApplication.java  # 应用启动类

## API接口文档

### 学生管理接口

方法	端点	描述
GET	/api/students	获取所有学生列表
GET	/api/students/{id}	根据ID查询学生
POST	/api/students	创建新学生
PUT	/api/students/{id}	更新学生信息
DELETE	/api/students/{id}	删除学生

### 课程管理接口

方法	端点	描述
GET	/api/courses	获取所有课程列表
GET	/api/courses/{id}	根据ID查询课程
POST	/api/courses	创建新课程
PUT	/api/courses/{id}	更新课程信息
DELETE	/api/courses/{id}	删除课程

### 选课管理接口

方法	端点	描述
GET	/api/enrollments	获取所有选课记录
GET	/api/enrollments/course/{courseId}	按课程查询选课记录
GET	/api/enrollments/student/{studentId}	按学生查询选课记录
POST	/api/enrollments	学生选课
DELETE	/api/enrollments/{id}	学生退选

## 运行指南

### 环境要求

* Java 8+
* Maven 3.6+
* Spring Boot 2.7+

### 启动步骤

1. 克隆项目到本地
2. 使用Maven编译项目：

   `mvn clean compile`

3. 运行应用：

    `mvn spring-boot:run`

4. 应用将在 http://localhost:8080 启动

### 初始化数据

系统启动时会自动创建测试数据：

* 2门课程：计算机科学导论、高等数学
* 2名学生：张三、李四

## 数据模型说明

### 学生(Student)

* id: 系统生成的UUID
* studentId: 学号（唯一）
* name: 姓名
* major: 专业
* grade: 年级
* email: 邮箱（唯一）
* createdAt: 创建时间

### 课程(Course)

* id: 系统生成的UUID
* code: 课程代码（唯一）
* title: 课程名称
* instructor: 授课教师
* schedule: 课程安排
* capacity: 课程容量
* enrolled: 已选人数

### 选课记录(Enrollment)

* id: 系统生成的UUID
* studentId: 学生ID（UUID）
* courseId: 课程ID（UUID）
* enrolledAt: 选课时间

## 业务规则

### 选课规则

1. 学生不能重复选择同一门课程
2. 选课人数不能超过课程容量
3. 退选后课程容量自动释放

### 删除约束

1. 有选课记录的学生不能被删除
2. 删除课程前需要先处理相关选课记录

### 数据验证

1. 学号唯一性验证
2. 邮箱格式和唯一性验证
3. 课程代码唯一性验证
4. 课程容量必须大于0

## 注意事项

1. 系统使用内存存储，重启后数据会丢失
2. 课程ID和学生ID均为系统生成的UUID，不是课程代码或学号
3. 选课时需要使用学生的UUID，不是学号
4. 所有时间字段均使用系统当前时间