# 课程选课系统 
## 项目概述

这是一个基于Spring Boot的课程选课管理系统，支持学生选课、课程管理、教师管理等功能。系统采用内存存储（ConcurrentHashMap）实现数据持久化。

该系统提供了完整的RESTful API，支持学生注册、课程创建、选课管理、容量控制等核心功能，具有良好的异常处理和统一的API响应格式。

## 系统架构

### 技术架构
- **后端框架**: Spring Boot 2.7+
- **数据存储**: 内存存储（ConcurrentHashMap）
- **API设计**: RESTful风格
- **异常处理**: 全局异常处理机制
- **响应格式**: 统一JSON响应格式

### 系统组件
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Controller    │◄──►│     Service      │◄──►│   Repository    │
│   层 (REST)     │    │   层 (业务逻辑)   │    │ 层 (数据访问)    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   API请求/响应   │    │  业务规则验证     │    │  内存数据存储    │
│   异常处理       │    │  事务管理        │    │  (ConcurrentHashMap) │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

## 功能特性

### 🎓 学生管理
- ✅ 学生信息注册与维护
- ✅ 学号唯一性验证
- ✅ 邮箱格式验证
- ✅ 删除前检查选课记录
- ✅ 学生信息更新

### 📚 课程管理
- ✅ 课程信息管理（课程代码、名称、教师、时间安排）
- ✅ 课程容量控制
- ✅ 自动生成课程ID
- ✅ 课程代码唯一性验证
- ✅ 课程信息更新和删除

### 🔄 选课系统
- ✅ 学生选课功能
- ✅ 退选功能
- ✅ 课程容量检查
- ✅ 防止重复选课
- ✅ 选课记录查询（按学生/按课程）
- ✅ 自动更新选课人数

### 👨‍🏫 教师管理
- ✅ 教师信息管理
- ✅ 课程与教师关联
- ✅ 教师信息维护

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.7+
- **构建工具**: Maven
- **语言**: Java 8+
- **数据存储**: 内存存储（ConcurrentHashMap）

### 开发工具
- **IDE**: 支持Spring Boot的任何Java IDE
- **API测试**: VS Code REST Client / Postman / curl
- **版本控制**: Git

## 项目结构

```
src/main/java/com/zjgsu/obl/course/Course/
├── common/
│   └── ApiResponse.java          # 统一API响应格式
├── controller/
│   ├── CourseController.java     # 课程控制器
│   ├── EnrollmentController.java # 选课控制器
│   └── StudentController.java    # 学生控制器
├── exception/
│   ├── GlobalExceptionHandler.java    # 全局异常处理
│   └── ResourceNotFoundException.java # 资源未找到异常
├── model/
│   ├── Course.java               # 课程实体
│   ├── Enrollment.java           # 选课记录实体
│   ├── Instructor.java           # 教师实体
│   ├── ScheduleSlot.java         # 课程时间安排
│   └── Student.java              # 学生实体
├── repository/
│   ├── CourseRepository.java     # 课程数据访问层
│   ├── EnrollmentRepository.java # 选课数据访问层
│   └── StudentRepository.java    # 学生数据访问层
├── service/
│   ├── CourseService.java        # 课程业务逻辑
│   ├── EnrollmentService.java    # 选课业务逻辑
│   └── StudentService.java       # 学生业务逻辑
└── CourseSystemApplication.java  # 应用启动类
```

## 安装和运行

### 环境要求

- **Java**: JDK 8 或更高版本
- **Maven**: 3.6 或更高版本
- **Spring Boot**: 2.7+

### 快速开始

1. **克隆项目**
   ```bash
   git clone <项目地址>
   cd course-system
   ```

2. **编译项目**
   ```bash
   mvn clean compile
   ```

3. **运行应用**
   ```bash
   mvn spring-boot:run
   ```

4. **验证启动**
   访问 `http://localhost:8080`，应用启动成功后可以看到API接口可用。

### 构建可执行JAR

```bash
mvn clean package
java -jar target/course-system.jar
```

## API接口文档

### 基础信息
- **Base URL**: `http://localhost:8080`
- **Content-Type**: `application/json`
- **响应格式**: 统一JSON格式

### 学生管理接口

| 方法 | 端点 | 描述 | 请求体 | 成功响应 |
|------|------|------|--------|----------|
| GET | `/api/students` | 获取所有学生列表 | - | 200 + 学生列表 |
| GET | `/api/students/{id}` | 根据ID查询学生 | - | 200 + 学生信息 |
| POST | `/api/students` | 创建新学生 | Student对象 | 201 + 创建的学生 |
| PUT | `/api/students/{id}` | 更新学生信息 | Student对象 | 200 + 更新的学生 |
| DELETE | `/api/students/{id}` | 删除学生 | - | 204 No Content |

**学生对象示例:**
```json
{
  "studentId": "S2024001",
  "name": "张三",
  "major": "计算机科学与技术",
  "grade": 2024,
  "email": "zhangsan@example.com"
}
```

### 课程管理接口

| 方法 | 端点 | 描述 | 请求体 | 成功响应 |
|------|------|------|--------|----------|
| GET | `/api/courses` | 获取所有课程列表 | - | 200 + 课程列表 |
| GET | `/api/courses/{id}` | 根据ID查询课程 | - | 200 + 课程信息 |
| POST | `/api/courses` | 创建新课程 | Course对象 | 201 + 创建的课程 |
| PUT | `/api/courses/{id}` | 更新课程信息 | Course对象 | 200 + 更新的课程 |
| DELETE | `/api/courses/{id}` | 删除课程 | - | 204 No Content |

**课程对象示例:**
```json
{
  "code": "CS101",
  "title": "计算机科学导论",
  "instructor": {
    "id": "T001",
    "name": "张教授",
    "email": "zhang@example.edu.cn"
  },
  "schedule": {
    "dayOfWeek": "MONDAY",
    "startTime": "08:00",
    "endTime": "10:00",
    "expectedAttendance": 50
  },
  "capacity": 60
}
```

### 选课管理接口

| 方法 | 端点 | 描述 | 请求体 | 成功响应 |
|------|------|------|--------|----------|
| GET | `/api/enrollments` | 获取所有选课记录 | - | 200 + 选课列表 |
| GET | `/api/enrollments/course/{courseId}` | 按课程查询选课记录 | - | 200 + 选课列表 |
| GET | `/api/enrollments/student/{studentId}` | 按学生查询选课记录 | - | 200 + 选课列表 |
| POST | `/api/enrollments` | 学生选课 | Enrollment对象 | 201 + 选课记录 |
| DELETE | `/api/enrollments/{id}` | 学生退选 | - | 204 No Content |

**选课对象示例:**
```json
{
  "studentId": "S2024001",
  "courseId": "course-uuid-123"
}
```

## 数据模型

### 学生(Student)
| 字段 | 类型 | 描述 | 约束 |
|------|------|------|------|
| id | String | 系统生成的UUID | 主键，自动生成 |
| studentId | String | 学号 | 唯一，必填 |
| name | String | 姓名 | 必填 |
| major | String | 专业 | 必填 |
| grade | Integer | 年级 | 必填 |
| email | String | 邮箱 | 唯一，必填，格式验证 |
| createdAt | LocalDateTime | 创建时间 | 自动生成 |

### 课程(Course)
| 字段 | 类型 | 描述 | 约束 |
|------|------|------|------|
| id | String | 系统生成的UUID | 主键，自动生成 |
| code | String | 课程代码 | 唯一，必填 |
| title | String | 课程名称 | 必填 |
| instructor | Instructor | 授课教师 | 必填 |
| schedule | ScheduleSlot | 课程安排 | 必填 |
| capacity | Integer | 课程容量 | 必填，>0 |
| enrolled | Integer | 已选人数 | 自动维护，初始为0 |

### 选课记录(Enrollment)
| 字段 | 类型 | 描述 | 约束 |
|------|------|------|------|
| id | String | 系统生成的UUID | 主键，自动生成 |
| studentId | String | 学生ID | 外键，必填 |
| courseId | String | 课程ID | 外键，必填 |
| enrolledAt | LocalDateTime | 选课时间 | 自动生成 |

### 教师(Instructor)
| 字段 | 类型 | 描述 | 约束 |
|------|------|------|------|
| id | String | 教师ID | 必填 |
| name | String | 教师姓名 | 必填 |
| email | String | 教师邮箱 | 必填 |

### 课程安排(ScheduleSlot)
| 字段 | 类型 | 描述 | 约束 |
|------|------|------|------|
| dayOfWeek | String | 星期几 | 必填 |
| startTime | String | 开始时间 | 必填，格式HH:mm |
| endTime | String | 结束时间 | 必填，格式HH:mm |
| expectedAttendance | Integer | 预计出席人数 | 可选 |

## 业务规则

### 学生管理规则
1. **学号唯一性**: 系统中不能存在相同学号的学生
2. **邮箱验证**: 邮箱必须符合标准格式且唯一
3. **删除保护**: 有选课记录的学生不能被删除
4. **信息更新**: 更新学生信息时保持学号和邮箱的唯一性

### 课程管理规则
1. **课程代码唯一性**: 课程代码必须在系统中唯一
2. **容量验证**: 课程容量必须大于0
3. **必填字段**: 课程代码、名称、教师、时间安排为必填项
4. **删除操作**: 删除课程时会移除相关选课记录

### 选课业务规则
1. **重复选课检查**: 学生不能重复选择同一门课程
2. **容量控制**: 选课人数不能超过课程容量
3. **存在性验证**: 选课前验证学生和课程是否存在
4. **人数统计**: 选课/退选时自动更新课程的已选人数

### 数据验证规则
1. **邮箱格式**: 必须符合标准邮箱格式
2. **时间格式**: 时间必须符合HH:mm格式
3. **数字范围**: 容量、年级等数字字段必须在合理范围内
4. **必填字段**: 所有标记为必填的字段不能为空

## 测试说明

### 测试环境搭建

1. **启动测试环境**
   ```bash
   mvn spring-boot:run
   ```

2. **验证服务状态**
   ```bash
   curl http://localhost:8080/api/students
   ```

### 单元测试

运行项目的单元测试套件：

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=StudentServiceTest
mvn test -Dtest=CourseServiceTest  
mvn test -Dtest=EnrollmentServiceTest

# 生成测试覆盖率报告
mvn jacoco:report
```

### API集成测试

项目提供了完整的HTTP测试文件 `test-api.http`，包含4个测试场景：

#### 测试场景1: 课程管理流程
- 创建多门课程
- 查询课程列表和详情
- 更新课程信息
- 删除课程
- 验证删除后的状态

#### 测试场景2: 选课业务流程
- 创建容量限制课程
- 学生正常选课
- 容量已满验证
- 重复选课验证
- 选课人数统计

#### 测试场景3: 学生管理流程
- 创建多个学生
- 查询学生信息
- 更新学生资料
- 删除保护验证
- 无记录学生删除

#### 测试场景4: 错误处理
- 查询不存在资源
- 必填字段验证
- 无效数据格式
- 重复数据验证
- 业务规则违反

### 使用Apifox测试

1. **环境配置**

   在Apifox中创建环境配置：

   **环境名称**: 本地开发环境

   **Base URL**: http://localhost:8080

   **变量**:baseUrl: http://localhost:8080

2. **导入API文档**

   将以下API集合导入Apifox：

   **学生管理API集合：**

```text
[{
  "name": "获取所有学生",
  "method": "GET",
  "url": "{{baseUrl}}/api/students"
}, {
  "name": "创建学生",
  "method": "POST", 
  "url": "{{baseUrl}}/api/students",
  "body": {
    "studentId": "S2024001",
    "name": "测试学生",
    "major": "计算机科学", 
    "grade": 2024,
    "email": "test@example.com"
  }
}]
```
3. **测试场景**

**场景1：完整的课程管理流程**

```javascript
// Apifox测试脚本示例
pm.test("创建课程成功", function() {
    pm.response.to.have.status(201);
    pm.expect(pm.response.json().code).to.eql(200);
});

pm.test("课程数据正确", function() {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.code).to.eql("CS202");
    pm.expect(jsonData.data.title).to.eql("数据结构与算法");
});
```
**场景2: 选课业务流程测试**

* 创建容量为2的课程

* 2名学生成功选课

* 第3名学生选课失败（容量已满）

* 验证重复选课限制

**场景3: 学生管理流程**

* 创建多个学生

* 更新学生信息

* 删除学生（有选课记录的保护）

4. **自动化测试**

在Apifox中创建测试套件：

```javascript
// 前置脚本 - 清理测试数据
pm.environment.set("studentId", "");
pm.environment.set("courseId", "");

// 后置脚本 - 保存变量
var jsonData = pm.response.json();
if (jsonData.data && jsonData.data.id) {
    pm.environment.set("studentId", jsonData.data.id);
}
```
5. **数据驱动测试**

   在Apifox中使用CSV数据文件进行参数化测试：
   **test_data.csv:**
```csv
studentId,name,major,grade,email,expectedStatus
S1001,张三,计算机科学,2024,zhangsan@test.com,201
S1002,李四,软件工程,2024,lisi@test.com,201
S1001,王五,网络安全,2024,wangwu@test.com,400
```

### 测试数据管理

**初始化数据**

系统启动时自动创建：

* **学生**: 张三(S2024001)、李四(S2024002)

* **课程**: 计算机科学导论(CS101)、高等数学(MATH201)

#### 测试用例数据

**课程测试数据:**

* CS202 - 数据结构与算法

* MATH202 - 离散数学

* EE201 - 电路原理

* SE401 - 软件工程实践（容量：2）

**学生测试数据:**

* S001 - 李明

* S002 - 王小红

* S003 - 陈小刚

### 预期测试结果
		
| 测试场景 | 预期结果 | 验证点 |
|--------|------|---------|
| 正常选课 | 201 Created | 选课成功，课程人数+1 |
| 重复选课| 400 Bad Request| "该学生已经选过这门课程" |
| 容量已满	| 400 Bad Request	 | "课程容量已满"|
| 学生不存在| 404 Not Found| "学生不存在"|
| 课程不存在| 404 Not Found| "课程不存在"|


## 错误处理

### 统一响应格式

所有API响应都遵循统一的格式：

**成功响应:**
```json
{
  "code": 200,
  "message": "Success",
  "data": {"..."}
}
```

**错误响应:**
```json
{
  "code": 400,
  "message": "错误描述",
  "data": null
}
```

### 常见错误码

| 状态码 | 含义 | 典型场景 |
|--------|------|----------|
| 200 | 成功 | 查询操作成功 |
| 201 | 创建成功 | 资源创建成功 |
| 204 | 无内容 | 删除操作成功 |
| 400 | 请求错误 | 数据验证失败、业务规则违反 |
| 404 | 未找到 | 资源不存在 |
| 500 | 服务器错误 | 系统内部错误 |

### 异常类型

1. **IllegalArgumentException**: 数据验证失败
2. **ResourceNotFoundException**: 资源未找到
3. **RuntimeException**: 系统运行时异常

## 部署说明

### 开发环境部署

1. **环境配置**
   ```bash
   # 设置Java环境
   export JAVA_HOME=/path/to/java
   
   # 编译打包
   mvn clean package -DskipTests
   ```

2. **运行应用**
   ```bash
   java -jar target/course-system.jar
   ```

### 生产环境建议

1. **配置调整**
   ```properties
   # 应用配置
   server.port=8080
   spring.profiles.active=prod
   ```

2. **监控配置**
   ```properties
   # 健康检查端点
   management.endpoints.web.exposure.include=health,info,metrics
   ```

## 开发指南

### 代码规范

1. **命名规范**
   - 类名使用大驼峰：`StudentService`
   - 方法名使用小驼峰：`createStudent`
   - 常量使用全大写：`MAX_CAPACITY`

2. **包结构规范**
   - `controller`: REST接口层
   - `service`: 业务逻辑层
   - `repository`: 数据访问层
   - `model`: 数据模型层
   - `exception`: 异常处理

### 扩展开发

1. **添加新实体**
   ```java
   // 1. 在model包创建实体类
   // 2. 在repository包创建Repository
   // 3. 在service包创建Service
   // 4. 在controller包创建Controller
   ```

2. **添加业务规则**
   ```java
   // 在相应的Service类中添加验证逻辑
   public Entity createEntity(Entity entity) {
       // 业务规则验证
       if (violatesRule(entity)) {
           throw new IllegalArgumentException("规则违反描述");
       }
       return repository.save(entity);
   }
   ```

## 故障排除

### 常见问题

1. **应用启动失败**
   - 检查Java版本是否符合要求
   - 检查端口8080是否被占用
   - 查看启动日志中的错误信息

2. **API请求失败**
   - 验证请求URL和HTTP方法
   - 检查请求体JSON格式
   - 查看应用日志中的异常信息

3. **数据不一致**
   - 重启应用重置内存数据
   - 检查业务逻辑验证
   - 验证外键关联关系

### 日志查看

```bash
# 查看应用日志
tail -f logs/application.log

# 查看特定级别的日志
grep "ERROR" logs/application.log

# 查看特定类的日志  
grep "StudentService" logs/application.log
```

