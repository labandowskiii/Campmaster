package com.campmaster.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.campmaster.modelo.Entities.Event;

public class EventPreviewController {

    @FXML
    private Label Lugar;

    @FXML
    private Label fechas;

    @FXML
    private Button infoButton;

    @FXML
    private Label nombreLabel;

    public void crearPreview(Event event){
        nombreLabel.setText(event.getNombre());
        Lugar.setText(event.getLugar());
        if (event.getFecha_inicio().equals(event.getFecha_fin()))
            fechas.setText(event.normalizaFecha(event.getFecha_inicio()));
        else{
            fechas.setText(event.normalizaFecha(event.getFecha_inicio())+" - "+ event.normalizaFecha(event.getFecha_fin()));
        }
        infoButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/EventMainView.fxml"));
                loader.load();
                EventMainViewController controller = loader.getController();
                controller.initialize(event);
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle(event.getNombre());
                stage.show();
                
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
