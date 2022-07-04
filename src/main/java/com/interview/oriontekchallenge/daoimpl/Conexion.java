package com.interview.oriontekchallenge.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Conexion() {

    }

    public static Connection conectar() throws SQLException {
        final String dbUrl = "jdbc:postgresql://localhost/clientela";
        final String user = "usuario";
        final String password = "1234";
        return DriverManager.getConnection(dbUrl, user, password);
    }
}
