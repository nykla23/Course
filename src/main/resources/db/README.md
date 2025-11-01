# 数据库配置与迁移说明

## 环境配置

### 开发环境 (H2)
- 数据库: H2 内存数据库
- 配置: `application-dev.yml`
- 控制台: http://localhost:8080/h2-console
- DDL 策略: create-drop (每次重启重建)

### 生产环境 (MySQL)
- 数据库: MySQL 8.0+
- 配置: `application-prod.yml`
- DDL 策略: validate (验证表结构)

## 初始化步骤

### 1. 创建数据库
```sql
CREATE DATABASE course_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;