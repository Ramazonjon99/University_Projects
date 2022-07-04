package com.example.groupchattingapp;

import javax.swing.*;
import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

public class DatabaseConnection {

    final static String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";

    final static String DB_URL="jdbc:mysql://localhost:49154/chatroom?useSSL=false&useUnicode=yes&characterEncoding=utf8";

    final static String USER="root";

    final static String PASS="mysqlpw";

    public static Connection connection(){
        try {
            Class.forName(JDBC_DRIVER);

            return getConnection(DB_URL,USER,PASS);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
