package com.campmaster.controlador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.campmaster.AppCampMaster;
import com.campmaster.modelo.Daos.Implementations.EventDAOImpl;
import com.campmaster.modelo.Daos.Implementations.ItemDAOImpl;
import com.campmaster.modelo.Entities.Event;

import com.campmaster.modelo.Entities.Item;
import com.campmaster.modelo.extra.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainViewController {

    @FXML
    private ScrollPane EventosApuntados;

    @FXML
    private ScrollPane EventosProximos;

    @FXML
    private Button EditButton;

    @FXML
    private Button LogOut;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button NuevaActividadButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button inventarioButton;

    @FXML
    private Button editInventarioButton;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Bienvenido, "+SessionManager.user.getNombre());
        if (SessionManager.getInstance().validatePermission("Administrador")){
            NuevaActividadButton.setVisible(true);
            inventarioButton.setVisible(true);
            inventarioButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/campmaster/resources/InventoryView.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.load()));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            NuevaActividadButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/campmaster/resources/NewEventView.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.load()));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (SessionManager.instance.validatePermission("Monitor")){
            inventarioButton.setVisible(true);
            inventarioButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/campmaster/resources/InventoryView.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.load()));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        LogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SessionManager.getInstance().logOut();
            }
        });

        LogOut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                SessionManager.getInstance().logOut();
                try {
                    AppCampMaster.setRoot("LogInView");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                refresh();
            }
        });

        EditButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/campmaster/resources/UserEditView.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));
                    stage.show();
                    SessionManager.getInstance().update();
                    stage.setOnHidden(event -> {
                        refresh();
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        java.util.Date javaDate = new java.util.Date();
        java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
        EventDAOImpl eventDao = new EventDAOImpl();
        ItemDAOImpl objectDAO = new ItemDAOImpl();
        setObjectsInventory();
        setEventosApuntados();
        setEventosProximos();


    }

    private void setObjectsInventory() {
        ItemDAOImpl objectDAO = new ItemDAOImpl();
        List<Item> items;
    }

    private void setEventosProximos(){
        EventDAOImpl eventDao = new EventDAOImpl();
        List <Event> eventos = eventDao.listAllEvents();
        HBox eventosContainer = new HBox();
        if (!eventos.isEmpty()){
            for (Event e : eventos){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/EventPreview.fxml"));
                    AnchorPane p = loader.load();
                    EventPreviewController controller = loader.getController();
                    controller.crearPreview(e);
                    eventosContainer.getChildren().add(p);
                }catch(Exception exc){
                    exc.printStackTrace();
                    Alert a = new Alert(AlertType.ERROR, "Error al cargar eventos", ButtonType.OK);
                }
            }
        }
        else{
            Label noEvents = new Label("No hay eventos próximos");
            eventosContainer.getChildren().add(noEvents);
        }
        EventosProximos.setContent(eventosContainer);
    }

    private void setEventosApuntados(){
        EventDAOImpl eventDao = new EventDAOImpl();
        List <Event> eventosApuntados = new ArrayList<Event>();
        if (SessionManager.getInstance().validatePermission("Administrador")){
            return;
        }
        else if (SessionManager.user.getTipo().equals("Monitor")){
            eventosApuntados = eventDao.listMonitorEvents(SessionManager.user.getDni());
        }
        else {
            eventosApuntados = eventDao.listParticipanteEvents(SessionManager.user.getDni());
        }
        HBox eventosApuntadosContainer = new HBox();
        if (!eventosApuntados.isEmpty()){
            for (Event e : eventosApuntados){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/EventPreview.fxml"));
                    AnchorPane p = loader.load();
                    EventPreviewController controller = loader.getController();
                    controller.crearPreview(e);
                    eventosApuntadosContainer.getChildren().add(p);
                }catch(Exception exc){
                    exc.printStackTrace();
                    Alert a = new Alert(AlertType.ERROR, "Error al cargar eventos", ButtonType.OK);
                }
            }
        }
        else{
            Label noEvents = new Label("No estás apuntado a ningún evento");
            eventosApuntadosContainer.getChildren().add(noEvents);
        }
        EventosApuntados.setContent(eventosApuntadosContainer);
    }



    private void refresh(){
        welcomeLabel.setText("Bienvenido, "+SessionManager.user.getNombre());
        setEventosApuntados();
        setEventosProximos();
    }

}
