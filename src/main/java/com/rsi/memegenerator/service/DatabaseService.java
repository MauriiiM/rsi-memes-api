package com.rsi.memegenerator.service;

import com.rsi.memegenerator.model.Meme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class DatabaseService {
    @Value("${amazonRdsProperties.dbName}")
    private String dbName;
    @Value("${amazonRdsProperties.endpoint}")
    private String endpoint;
    @Value("${amazonRdsProperties.region}")
    private String region;
    @Value("${amazonRdsProperties.port}")
    private String port;
    @Value("${amazonRdsProperties.username}")
    private String username;
    @Value("${amazonRdsProperties.password}")
    private String password;
    private Statement setupStatement;

    /**
     * Insert a meme to the database
     *
     * @param meme containing fields needed to insert into database
     */
    public void insert(Meme meme) throws SQLException {
        Connection connection = openRemoteConnection(false);
        String query = "INSERT INTO image (image_id, image_s3_url, image_upload_date, image_file_name) VALUES (?, ?, ? ,?)";
        PreparedStatement memeTableStmt = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        memeTableStmt.setLong(1, meme.getId());
        memeTableStmt.setString(2, meme.getS3url());
        memeTableStmt.setTimestamp(3, meme.getUploadDate());
        memeTableStmt.setString(4, meme.getFilename());
        memeTableStmt.executeUpdate();
        ResultSet resultSet = memeTableStmt.getGeneratedKeys();
        resultSet.next();
        meme.setId(resultSet.getLong(1));

        PreparedStatement tagTableStmt = connection
                .prepareStatement("INSERT INTO tag (tag_string, tag_image_id_ref) VALUES(?, " + meme.getId() + ")");
        int numTags = meme.getTags().length;
        for (int i = 0; i < numTags; i++) {
            tagTableStmt.setString(1, meme.getTags()[i]);
            tagTableStmt.addBatch();
        }
        tagTableStmt.executeBatch();
        connection.commit();
        System.out.println("Query complete. Closing Statements.");
        memeTableStmt.close();
        tagTableStmt.close();
        closeRemoteConnection(connection);
    }

    public String[] selectTags(String[] tags) {
        ArrayList<String> urls = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT i.image_id, i.image_s3_url FROM image AS i " +
                "INNER JOIN tag AS t " +
                "ON t.tag_image_id_ref = i.image_id " +
                "WHERE t.tag_string = \"" + tags[0] + "\"");
        try {
            Connection connection = openRemoteConnection();
            if (tags.length > 1)
                for (int i = 1; i < tags.length; i++) {
                    query.append(" OR t.tag_string = \"" + tags[i] + "\"");
                }
            System.out.println(query.toString());
            PreparedStatement stmt = connection.prepareStatement(query.toString());
            ResultSet resultSet = stmt.executeQuery();

            //keeps going row by row and get value of column
            while (resultSet.next()) urls.add(resultSet.getString("image_s3_url"));

            closeRemoteConnection(connection);
        } catch (SQLException e) { e.printStackTrace(); }
        return urls.toArray(new String[0]);
    }

    /**
     * Opens connection to database using application.yml fields
     * Connection should be "opened" and "closed" on every query.
     * Autocommits defaults to true.
     */
    private Connection openRemoteConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
        System.out.println("Connecting to database: " + jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        return connection;
    }

    private Connection openRemoteConnection(boolean autoCommit) throws SQLException {
        Connection connection = openRemoteConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    private void closeRemoteConnection(Connection connection) throws SQLException {
        System.out.println("Closing connection.");
        connection.close();
        connection = null;
    }
}

