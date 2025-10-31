package com.zjgsu.obl.course.Course.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseTest implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ 数据库连接成功!");
            System.out.println("数据库: " + conn.getCatalog());
            System.out.println("URL: " + conn.getMetaData().getURL());
            System.out.println("驱动: " + conn.getMetaData().getDriverName());
        } catch (Exception e) {
            System.out.println("❌ 数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}