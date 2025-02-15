package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;
import com.campmaster.modelo.Entities.User;
import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MonitorPickController {
    @FXML
    private ListView<User> monitorList;

    @FXML
    private Button confirmButton;

    @FXML
    private void initialize(){

    }

    public void initializeForNew(){
        UserDAOImpl userDAO = new UserDAOImpl();
        List<User> monitors = userDAO.readAllMonitors();
        monitorList.getItems().addAll(monitors);
        monitorList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        confirmButton.setOnAction(e -> {
            List<String> selectedMonitors = new ArrayList<String>();
            for (User monitor : monitorList.getSelectionModel().getSelectedItems()){
                selectedMonitors.add(monitor.getDni());
            }
            SessionManager.getInstance().setAuxMonitorList(selectedMonitors);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        });
    }

    public void initializeForEdit(List<String> monitors){
        UserDAOImpl userDAO = new UserDAOImpl();
        List<User> allMonitors = userDAO.readAllMonitors();
        monitorList.getItems().addAll(allMonitors);
        monitorList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        for (String dni : monitors){
            User monitor= userDAO.read(dni);
            monitorList.getSelectionModel().select(monitor);
        }

        confirmButton.setOnAction(e -> {
            List<String> selectedMonitors = new ArrayList<String>();
            for (User monitor : monitorList.getSelectionModel().getSelectedItems()){
                selectedMonitors.add(monitor.getDni());
            }
            SessionManager.getInstance().setAuxMonitorList(selectedMonitors);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        });
    }
}
