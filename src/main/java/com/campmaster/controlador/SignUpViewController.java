package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.SettingsDAOImpl;
import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

import com.campmaster.AppCampMaster;
import com.campmaster.modelo.Entities.User;
import com.campmaster.modelo.extra.PasswordValidator;

public class SignUpViewController {

    @FXML
    private TextField dni;

    @FXML
    private TextField name;

    @FXML
    private TextField apellidos;

    @FXML
    private PasswordField pw;

    @FXML
    private Button regUserButton;

    @FXML
    private PasswordField repeat_pw;

    @FXML
    private TextField correo;

    @FXML
    private RadioButton ParticipanteButton;

    @FXML
    private RadioButton MonitorButton;

    @FXML
    private RadioButton AdminButton;

    @FXML
    private PasswordField pw_type;

    @FXML
    private ToggleGroup grupoTipo=new ToggleGroup();

    @FXML
    private Button returnLogin;

    @FXML
    private TextField nombreTutor;

    @FXML
    private TextField telefonoTutor;

    UserDAOImpl userDAO = new UserDAOImpl();

    @FXML
    protected void registerUser() throws IOException {
        String dni = this.dni.getText();
        String name = this.name.getText();
        String apellidos=this.apellidos.getText();
        String correo=this.correo.getText();
        String pw = this.pw.getText();
        String repeat_pw = this.repeat_pw.getText();
        String tipo = ((RadioButton) grupoTipo.getSelectedToggle()).getText();
        User user= new User(dni, pw, name, apellidos, correo, "", "", "");
        if (!checkType(tipo)){
            return;
        }
        else if (!freeDni()){
            Alert a = new Alert(AlertType.ERROR, "El DNI ya está registrado.", ButtonType.OK);
            a.show();
        }
        else if (!PasswordValidator.isValid(pw)) {
            Alert a = new Alert(AlertType.ERROR, "La contraseña debe tener al menos 8 caracteres, 1 numero, 1 letra mayúscula y 1 letra minúscula.", ButtonType.OK);
            a.show();
        }
        else if (!checkPw()){
            Alert a = new Alert(AlertType.ERROR, "Las contraseñas no coinciden.", ButtonType.OK);
            a.show();
        }
        else if (checkPw()&&freeDni()) {
            user.setTipo(tipo);
            if (userDAO.create(user)) {
                Alert a = new Alert(AlertType.CONFIRMATION, "Usuario registrado correctamente.", ButtonType.OK);
                a.show();
                AppCampMaster.setRoot("LogInView");
            }
        }
        else {
            Alert a = new Alert(AlertType.ERROR, "Error en el registro.", ButtonType.OK);
            a.show();
        }
    }

    @FXML
    protected void initialize() {
        regUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AdminButton.setToggleGroup(grupoTipo);
                    MonitorButton.setToggleGroup(grupoTipo);
                    ParticipanteButton.setToggleGroup(grupoTipo);
                    registerUser();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        returnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AppCampMaster.setRoot("LogInView");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        
    }

    private boolean checkPw() {
        return pw.getText().equals(repeat_pw.getText());
    }

    private boolean freeDni() {
        return userDAO.validateDni(dni.getText());
    }

    private boolean checkType(String tipo){
        String pwtype= pw_type.getText();
        SettingsDAOImpl settings = new SettingsDAOImpl();
        switch (tipo) {
            case "Monitor":
                if (pwtype.isEmpty() || !settings.validateMonitPw(pwtype)) {
                    Alert a = new Alert(AlertType.ERROR, "Contraseña de monitor incorrecta.", ButtonType.OK);
                    a.show();
                    return false;
                }
                return true;
            case "Administrador":
                if (pwtype==null||!settings.validateAdminPw(pwtype)) {
                    Alert a = new Alert(AlertType.ERROR, "Contraseña de administrador incorrecta,", ButtonType.OK);
                    a.show();
                    return false;
                }
                return true;
            case "Participante":
                if (nombreTutor.getText().isEmpty() || telefonoTutor.getText().isEmpty()) {
                    Alert a = new Alert(AlertType.ERROR, "Faltan campos por rellenar.", ButtonType.OK);
                    a.show();
                    return false;
                }
                return true;
            default:
                return false;
        }
    }
}