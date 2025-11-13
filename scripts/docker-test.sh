#!/bin/bash

echo "=== 开始校园选课系统容器化测试 ==="

# 构建镜像
echo "1. 构建 Docker 镜像..."
docker compose build

# 启动服务
echo "2. 启动所有服务..."
docker compose up -d

# 等待服务启动
echo "3. 等待服务启动..."
sleep 30

# 检查服务状态
echo "4. 检查服务状态..."
docker compose ps

# 检查应用健康状态
echo "5. 检查应用健康状态..."
for i in {1..10}; do
    if curl -f http://localhost:8081/health/db > /dev/null 2>&1; then
        echo "应用健康检查通过"
        break
    else
        echo "等待应用启动... ($i/10)"
        sleep 10
    fi
done

# 测试数据库健康检查
echo "6. 测试数据库连接..."
curl -s http://localhost:8081/health/db | grep -q '"status":"UP"' && echo "✅ 数据库连接正常" || echo "❌ 数据库连接异常"

# 测试课程 API
echo "7. 测试课程 API..."
echo "获取课程列表:"
curl -s http://localhost:8081/api/courses

# 创建测试课程
echo ""
echo "8. 创建测试课程..."
CREATE_COURSE_RESPONSE=$(curl -s -X POST http://localhost:8081/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "courseCode": "DOCKER101",
    "title": "Docker容器化部署",
    "capacity": 30,
    "instructorId": "T999",
    "instructorName": "容器专家",
    "instructorEmail": "docker@example.edu.cn",
    "scheduleDay": "MONDAY",
    "scheduleStartTime": "14:00",
    "scheduleEndTime": "16:00",
    "expectedAttendance": 25
  }')
echo "创建课程响应: $CREATE_COURSE_RESPONSE"

# 创建测试学生
echo ""
echo "9. 创建测试学生..."
CREATE_STUDENT_RESPONSE=$(curl -s -X POST http://localhost:8081/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "S2024004",
    "name": "容器测试学生",
    "major": "软件工程",
    "grade": 2024,
    "email": "docker@test.com"
  }')
echo "创建学生响应: $CREATE_STUDENT_RESPONSE"

# 测试选课功能
echo ""
echo "10. 测试选课功能..."
# 首先获取课程ID和学生ID
COURSE_ID=$(curl -s http://localhost:8081/api/courses | grep -o '"id":"[^"]*' | head -1 | cut -d'"' -f4)
STUDENT_ID=$(curl -s http://localhost:8081/api/students | grep -o '"id":"[^"]*' | head -1 | cut -d'"' -f4)

if [ -n "$COURSE_ID" ] && [ -n "$STUDENT_ID" ]; then
    CREATE_ENROLLMENT_RESPONSE=$(curl -s -X POST http://localhost:8081/api/enrollments \
      -H "Content-Type: application/json" \
      -d "{
        \"studentId\": \"$STUDENT_ID\",
        \"courseId\": \"$COURSE_ID\",
        \"status\": \"ACTIVE\"
      }")
    echo "创建选课响应: $CREATE_ENROLLMENT_RESPONSE"
else
    echo "无法获取课程或学生ID，跳过选课测试"
fi

# 测试查询功能
echo ""
echo "11. 测试查询功能..."
echo "查询可用课程:"
curl -s http://localhost:8081/api/courses/available

echo ""
echo "按专业查询学生:"
curl -s "http://localhost:8080/api/students/major?major=软件工程"

echo ""
echo "查询所有选课记录:"
curl -s http://localhost:8081/api/enrollments

# 验证数据持久化
echo ""
echo "12. 验证数据持久化..."
echo "停止容器..."
docker compose down

echo "重新启动容器..."
docker compose up -d
sleep 20

echo "验证重启后健康状态:"
curl -s http://localhost:8081/health/db | grep -q '"status":"UP"' && echo "✅ 重启后数据库连接正常" || echo "❌ 重启后数据库连接异常"

echo "验证课程数据是否持久化:"
curl -s http://localhost:8081/api/courses | grep -q "DOCKER101" && echo "✅ 课程数据持久化成功" || echo "❌ 课程数据持久化失败"

echo "验证学生数据是否持久化:"
curl -s http://localhost:8081/api/students | grep -q "S2024004" && echo "✅ 学生数据持久化成功" || echo "❌ 学生数据持久化失败"

echo "=== Docker 环境测试完成 ==="

# 输出有用的调试信息
echo ""
echo "=== 调试信息 ==="
echo "查看应用日志: docker compose logs app"
echo "查看数据库日志: docker compose logs mysql"
echo "进入应用容器: docker exec -it coursehub-app bash"
echo "进入数据库: docker exec -it coursehub-mysql mysql -u coursehub_user -p coursehub"
echo "密码: coursehub_pass123"
echo "测试API端点:"
echo "  curl http://localhost:8080/api/courses"
echo "  curl http://localhost:8080/api/students"
echo "  curl http://localhost:8080/api/enrollments"
echo "  curl http://localhost:8080/health/db"