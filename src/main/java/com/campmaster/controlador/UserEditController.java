package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.DocumentDAOImpl;
import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;
import com.campmaster.modelo.Entities.User;
import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class UserEditController {
    @FXML
    private TextField apellidosField;

    @FXML
    private VBox buttonBox;

    @FXML
    private TextField correoField;

    @FXML
    private TextField dniField;

    @FXML
    private Button documento1;

    @FXML
    private Button documento2;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField nombreTutorField;

    @FXML
    private TextField telefonoTutorField;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        if (SessionManager.getInstance().validatePermission("Monitor", "Administrador")) {
            nombreTutorField.setDisable(true);
            telefonoTutorField.setDisable(true);
        }
        if (SessionManager.getInstance().validatePermission("Administrador")) {
            documento1.setVisible(false);
            documento2.setVisible(false);
        }
        rellenaCampos();
        if (SessionManager.getInstance().validatePermission("Monitor")) {
            buttonBox.getChildren().remove(documento2);
            DocumentDAOImpl documentDAO = new DocumentDAOImpl();
            if (documentDAO.existsDocument(SessionManager.user.getDni()+"-CERT")) {
                documento1.setText("Modificar titulo");
                documento1.setOnAction(event -> {
                    try {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                        fc.setTitle("Selecciona el documento");
                        String path=fc.showOpenDialog(documento1.getScene().getWindow()).getAbsolutePath();
                        if (!documentDAO.updateExistingDoc(documentDAO.read(SessionManager.user.getDni()+"-CERT"), path, "CERT")){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error actualizando el documento", ButtonType.OK);
                            alert.show();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            else {
                documento1.setText("Subir Certificado");
            }
            documento1.setOnAction(event -> {
                try {
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                    fc.setTitle("Selecciona el documento");
                    String path=fc.showOpenDialog(documento1.getScene().getWindow()).getAbsolutePath();
                    if (!documentDAO.uploadDoc(path, "CERT")){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error subiendo el documento", ButtonType.OK);
                        alert.show();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                        alert.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        else if (SessionManager.getInstance().validatePermission("Participante")) {
            DocumentDAOImpl documentDAO = new DocumentDAOImpl();
            if (documentDAO.existsDocument(SessionManager.user.getDni()+"-DNI")) {
                documento1.setText("Modificar DNI");
                documento1.setOnAction(event -> {
                    try {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                        fc.setTitle("Selecciona el documento");
                        String path=fc.showOpenDialog(documento1.getScene().getWindow()).getAbsolutePath();
                        if (!documentDAO.updateExistingDoc(documentDAO.read(SessionManager.user.getDni()+"-DNI"), path, "DNI")){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error actualizando el documento", ButtonType.OK);
                            alert.show();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            else {
                documento1.setText("Subir DNI");
                documento1.setOnAction(event -> {
                    try {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                        fc.setTitle("Selecciona el documento");
                        String path=fc.showOpenDialog(documento1.getScene().getWindow()).getAbsolutePath();
                        if (!documentDAO.uploadDoc(path, "DNI")){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error subiendo el documento", ButtonType.OK);
                            alert.show();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
                if (documentDAO.existsDocument(SessionManager.user.getDni()+"-TS")) {
                    documento2.setText("Modificar Tarjeta Sanitaria");
                    documento2.setOnAction(event -> {
                        try {
                            FileChooser fc = new FileChooser();
                            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                            fc.setTitle("Selecciona el documento");
                            String path=fc.showOpenDialog(documento2.getScene().getWindow()).getAbsolutePath();
                            if (!documentDAO.updateExistingDoc(documentDAO.read(SessionManager.user.getDni()+"-TS"), path, "TS")){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Error actualizando el documento", ButtonType.OK);
                                alert.show();
                            }
                            else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                                alert.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            else {
                documento2.setText("Subir Tarjeta Sanitaria");
                documento2.setOnAction(event -> {
                    try {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
                        fc.setTitle("Selecciona el documento");
                        String path=fc.showOpenDialog(documento2.getScene().getWindow()).getAbsolutePath();
                        if (!documentDAO.uploadDoc(path, "TS")){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error subiendo el documento", ButtonType.OK);
                            alert.show();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Documento subido correctamente", ButtonType.OK);
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            nombreTutorField.setText(SessionManager.getInstance().getUser().getNombreTutor());
            telefonoTutorField.setText(SessionManager.getInstance().getUser().getNumeroTutor());
        }

        saveButton.setOnAction(event -> {
            if (nombreField.getText().isEmpty() || apellidosField.getText().isEmpty() || dniField.getText().isEmpty() || correoField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Rellena todos los campos", ButtonType.OK);
                alert.show();
            } else {
                User user=new User(SessionManager.getInstance().getUser());
                user.setNombre(nombreField.getText());
                user.setApellidos(apellidosField.getText());
                user.setDni(dniField.getText());
                user.setCorreo(correoField.getText());
                if (SessionManager.getInstance().validatePermission("Participante")){
                    user.setNombreTutor(nombreTutorField.getText());
                    user.setNumeroTutor(telefonoTutorField.getText());
                }
                if (user.validate()) {
                    UserDAOImpl userDAO = new UserDAOImpl();
                    userDAO.update(user);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Datos actualizados correctamente", ButtonType.OK);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Datos incorrectos", ButtonType.OK);
                    alert.show();
                }

            }
        });
    }

    private void rellenaCampos() {
        nombreField.setText(SessionManager.getInstance().getUser().getNombre());
        apellidosField.setText(SessionManager.getInstance().getUser().getApellidos());
        dniField.setText(SessionManager.getInstance().getUser().getDni());
        correoField.setText(SessionManager.getInstance().getUser().getCorreo());
        if (SessionManager.getInstance().validatePermission("Participante")){
            nombreTutorField.setText(SessionManager.getInstance().getUser().getNombreTutor());
            telefonoTutorField.setText(SessionManager.getInstance().getUser().getNumeroTutor());
        }
    }

}
