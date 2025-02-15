package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.ItemDAOImpl;
import com.campmaster.modelo.Entities.Item;
import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryViewController {
    @FXML
    private Button editButton;

    @FXML
    private ListView<Item> inventoryList;

    @FXML
    private Button newItemButton;

    @FXML
    private void initialize() {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        inventoryList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        inventoryList.getItems().addAll(itemDAO.readAll());
        if (SessionManager.getInstance().getUser().getTipo().equals("Administrador")) {
            editButton.setVisible(true);
            newItemButton.setVisible(true);
            editButton.setOnAction(event -> {
                Item selectedItem = inventoryList.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // setRoot to the item view with the selected item
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/ItemView.fxml"));
                    Stage stage = new Stage();
                    try {
                        stage.setScene(new Scene(loader.load()));
                        ItemViewController controller = loader.getController();
                        controller.initialize(selectedItem);
                        stage.setOnHidden(e -> reload());
                        stage.setTitle("Editar item");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.show();

                }
                else{
                    // Show an alert if no item is selected
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Seleccione un item para editar", ButtonType.OK);
                    alert.show();
                }
            });

            newItemButton.setOnAction(event -> {
                // Open a new window to create a new item
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/ItemView.fxml"));
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(loader.load()));
                    ItemViewController controller = loader.getController();
                    controller.initialize(null);
                    stage.setOnHidden(e -> reload());
                    stage.setTitle("Nuevo item");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            });
        }
    }

    private void reload() {
        inventoryList.getItems().clear();
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        inventoryList.getItems().addAll(itemDAO.readAll());
    }
}
