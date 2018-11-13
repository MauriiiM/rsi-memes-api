package com.rsi.memegenerator.service;

import com.rsi.memegenerator.model.Meme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {
    @Value("${amazonRdsProperties.dbName}")
    private String dbName;
    @Value("${amazonRdsProperties.hostname}")
    private String hostname;
    @Value("${amazonRdsProperties.region}")
    private String region;
    @Value("${amazonRdsProperties.port}")
    private String port;
    @Value("${amazonRdsProperties.username}")
    private String username;
    @Value("${amazonRdsProperties.password}")
    private String password;
    private Connection connection;
    private Statement setupStatement;

    /**
     * Insert a meme to the database
     * @param meme
     */
    public void insertBlank(Meme meme) {
        try {
            openRemoteConnection();
            PreparedStatement stmt = connection.prepareStatement("");
            stmt.execute();
            stmt.close();
            closeRemoteConnection();
        } catch (SQLException e) { e.printStackTrace(); }
    }

//    void insertCreated(Meme meme) {
//        connection.prepareStatement("")
//    }

    /**
     * opens connection to database using application.yml fields
     * Connection should be "opened" and "closed" on every query.
     */
    private void openRemoteConnection() {
        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
        System.out.println("Connecting to database: " + jdbcUrl);
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void closeRemoteConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
