package com.example.groupchattingapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;

public class LogRegController {
    @FXML
    private AnchorPane LogRegPage;

    @FXML
    private Pane pnSingIn;

    @FXML
    private PasswordField passWord;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField userName;
    @FXML
    private Pane pnSignUp;

    @FXML
    private ImageView btnBack;

    @FXML
    private TextField regFirstName;

    @FXML
    private DatePicker regAge;

    @FXML
    private TextField regName;

    @FXML
    private PasswordField regPass;

    @FXML
    private PasswordField regPass1;
    public static String userID, pass;



    Connection conn;
    ResultSet rs=null;
    Statement stmt=null;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(btnSignUp)) {
            pnSingIn.setVisible(false);
            pnSignUp.setVisible(true);
        }
        userName.setText("");
        passWord.setText("");

    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        if (event.getSource().equals(btnBack)) {
            pnSingIn.setVisible(true);
            pnSignUp.setVisible(false);
        }
    }

    @FXML
    void login(ActionEvent event) {

        conn = DatabaseConnection.connection();
        userID = userName.getText();
        pass = passWord.getText();
        String sql = "select * from users where userID = '" + userID + "' and password = '" + pass + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "登录成功");
                Stage stage = (Stage) LogRegPage.getScene().getWindow();
                stage.close();
                Chatroom();
            } else {
                JOptionPane.showMessageDialog(null, "密码或用户名错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void Chatroom() {
        try {
            Stage stage = new Stage();
            stage.setTitle("聊天室");
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("room.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registration(ActionEvent event)  {
        conn = DatabaseConnection.connection();
        try {
            stmt = conn.createStatement();
            String name, age, userID, pass, pass1;
            name = regFirstName.getText();
            age = regAge.getValue().toString();
            userID = regName.getText();
            pass = regPass.getText();
            pass1 = regPass1.getText();

            if (name.equals("") || age.equals("") || userID.equals("") || pass.equals("") || pass1.equals("")) {
                JOptionPane.showMessageDialog(null, "请输入完整信息");
            } else if (!pass.equals(pass1)) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致");
            } else if (stmt.executeQuery("select * from users where userID = '" + userID + "'").next()) {
                JOptionPane.showMessageDialog(null, "用户ID已存在");
            } else {
                String query = "INSERT INTO users (name, age, userID, password) VALUES ('" + name + "', '" + age + "', '" + userID + "', '" + pass + "')";
                stmt.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "注册成功");
                pnSignUp.setVisible(false);
                pnSingIn.setVisible(true);
                userName.setText(userID);
                passWord.setText(pass);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

}
