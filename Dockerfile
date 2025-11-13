# 多阶段构建
# 第一阶段：构建阶段
FROM maven:3.9-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建应用（跳过测试）
RUN mvn clean package -DskipTests

# 第二阶段：运行阶段
FROM openjdk:17-slim

# 安装 curl 用于健康检查
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 创建非root用户
RUN groupadd -r spring && useradd -r -g spring spring

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 创建日志目录并设置权限
RUN mkdir -p /app/logs && chown -R spring:spring /app

# 切换到非root用户
USER spring

# 暴露端口
EXPOSE 8080

# 设置 JVM 参数
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]