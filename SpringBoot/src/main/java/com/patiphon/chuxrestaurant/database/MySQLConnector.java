package com.patiphon.chuxrestaurant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    public static final String URL = "jdbc:mysql://csproject.sit.kmutt.ac.th:3306/db63130500216";
    public static final String USERNAME = "63130500216";
    public static final String PASSWORD = "abcd1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

