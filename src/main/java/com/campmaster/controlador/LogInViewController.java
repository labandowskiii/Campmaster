package com.campmaster.controlador;

import java.io.IOException;

import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;

import com.campmaster.AppCampMaster;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LogInViewController {

    @FXML
    private Button login;
    @FXML
    private Button signup;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;

    UserDAOImpl db;

    @FXML
    protected boolean logInAction() {
        String dni = user.getText();
        String pass = password.getText();
        return db.login(dni, pass);
    }

    @FXML
    EventHandler<ActionEvent> loginClick = new
                         EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (logInAction()){
                    Alert a=new Alert(AlertType.CONFIRMATION,"Login correcto.", ButtonType.OK);
                    a.show();
                    try {
                        AppCampMaster.setRoot("MainView");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    Alert a=new Alert(AlertType.ERROR,"Login incorrecto.", ButtonType.OK);
                    a.show();
                }
            }
        };
    @FXML
    EventHandler<ActionEvent> signupClick = new
                         EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    AppCampMaster.setRoot("SignUpView");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
    @FXML
    public void initialize() {
        db = new UserDAOImpl();
        login.setOnAction(loginClick);
        signup.setOnAction(signupClick);
    }
}