package com.example.groupchattingapp;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class RoomController extends Thread implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label clientName;

    @FXML
    private TextArea msgRoom;

    @FXML
    private TextField msgField;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(LogRegController.userID + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("拜")) {
                    break;
                }
                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSendEvent(MouseEvent event) {
        send();
        System.out.println(LogRegController.userID);
    }

    public void send() {
        String msg = msgField.getText();
        writer.println(LogRegController.userID + ": " + msg);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText("我: " + msg + "\n");
        msgField.setText("");
        if(msg.equalsIgnoreCase("拜") || (msg.equalsIgnoreCase("退出"))) {
            System.exit(0);
        }
    }
    @FXML
    void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clientName.setText(LogRegController.userID);
        connectSocket();

    }

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8888);
            reader = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(LogRegController.userID + ": " + "成功连接到服务器");
            start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}