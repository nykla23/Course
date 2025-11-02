package com.zjgsu.obl.course.Course.controller;

import com.zjgsu.obl.course.Course.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkDbHealth() {
        Map<String, Object> healthInfo = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {

            boolean tablesExist = checkTablesExist(conn);
            healthInfo.put("status", "UP");
            healthInfo.put("database", conn.getMetaData().getDatabaseProductName());
            healthInfo.put("version", conn.getMetaData().getDatabaseProductVersion());
            healthInfo.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(ApiResponse.success("数据库连接正常", healthInfo));
        } catch (Exception e) {
            healthInfo.put("status", "DOWN");
            healthInfo.put("error", e.getMessage());
            healthInfo.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(503)
                    .body(ApiResponse.error(503, "数据库连接失败: " + e.getMessage()));
        }
    }

    private boolean checkTablesExist(Connection conn) throws SQLException {
        String[] tables = {"courses", "students", "enrolments"};
        for (String table : tables) {
            try (ResultSet rs = conn.getMetaData().getTables(null, null, table, null)) {
                if (!rs.next()) {
                    return false;
                }
            }
        }
        return true;
    }
}