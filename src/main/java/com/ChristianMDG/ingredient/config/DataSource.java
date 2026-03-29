
package com.ChristianMDG.ingredient.config;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class DataSource {
    private final Environment env;
    public Connection getConnection() {
        try {
            String url = env.getProperty("db.url");
            String user = env.getProperty("db.user");
            String password = env.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }
}
