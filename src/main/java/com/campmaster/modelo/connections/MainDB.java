package com.campmaster.modelo.connections;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public final class MainDB {
    private Dotenv dotenv = Dotenv.configure().load();
    private String host = dotenv.get("DB_HOST");
    private String port = dotenv.get("DB_PORT");
    private String dbName = dotenv.get("DB_NAME");
    private String userName = dotenv.get("DB_USER");
    private String password = dotenv.get("DB_PASS");
    private Connection c;
    public static MainDB singleton;

    private  MainDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName + "?sslmode=require", userName, password);
            singleton = this;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static MainDB getInstance() {
        if (singleton == null) {
            singleton = new MainDB();
        }
        return singleton;
    }

    public Connection getConnection() {
        return c;
    }

}
