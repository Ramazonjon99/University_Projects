module com.example.groupchattingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.groupchattingapp to javafx.fxml;
    exports com.example.groupchattingapp;
}