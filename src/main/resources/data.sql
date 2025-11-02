-- 初始化课程数据
INSERT  INTO courses (id, course_code, title, capacity, enrolled_count, instructor_id, instructor_name, instructor_email, schedule_day, schedule_start_time, schedule_end_time, expected_attendance) VALUES
(UUID(), 'CS101', '计算机科学导论', 60, 0, 'T001', '张教授', 'zhang@example.edu.cn', 'MONDAY', '08:00', '10:00', 50),
(UUID(), 'MATH201', '高等数学', 50, 0, 'T002', '李教授', 'li@example.edu.cn', 'TUESDAY', '10:00', '12:00', 40),
(UUID(), 'ENG101', '大学英语', 80, 0, 'T003', '王老师', 'wang@example.edu.cn', 'WEDNESDAY', '14:00', '16:00', 60);

-- 初始化学生数据
INSERT  INTO students (id, student_id, name, major, grade, email, created_at) VALUES
(UUID(), 'S2024001', '张三', '计算机科学与技术', 2024, 'zhangsan@example.com', CURRENT_TIMESTAMP),
(UUID(), 'S2024002', '李四', '软件工程', 2024, 'lisi@example.com', CURRENT_TIMESTAMP),
(UUID(), 'S2024003', '王五', '数学', 2024, 'wangwu@example.com', CURRENT_TIMESTAMP);