package com.campmaster.controlador;
import com.campmaster.AppCampMaster;
import com.campmaster.modelo.Daos.Implementations.AssistanceMonitorsDAO;
import com.campmaster.modelo.Daos.Implementations.EventDAOImpl;
import com.campmaster.modelo.Entities.Event;
import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class NewEventController {


    @FXML
    private Button crear_button;

    @FXML
    private TextArea desc_field;

    @FXML
    private DatePicker fecha_fin;

    @FXML
    private DatePicker fecha_inicio;

    @FXML
    private TextField lugar_actividad;

    @FXML
    private Button monit_button;

    @FXML
    private TextField nombre_actividad;

    @FXML
    private Label num_monit;

    @FXML
    private Spinner<Integer> max_participantes;

    @FXML
    private void initialize() {
        crear_button.setOnAction(e -> {
            if (!createEvent()){
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Evento creado correctamente", ButtonType.OK);
                alert.show();
                SessionManager.getInstance().getAuxMonitorList().clear();
            }
            try {
                AppCampMaster.setRoot("MainView");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

       monit_button.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/MonitorPickView.fxml"));
                loader.load();
                MonitorPickController controller = loader.getController();
                controller.initializeForNew();
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setTitle("Selecciona monitores");
                stage.show();
                stage.setOnHidden(event -> {
                    num_monit.setText("Monitores seleccionados: " + SessionManager.getInstance().getAuxMonitorList().size());
                });

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0);
        max_participantes.setValueFactory(valueFactory);
    }


    private boolean createEvent() {
        if (nombre_actividad.getText().isEmpty() || lugar_actividad.getText().isEmpty() || fecha_inicio.getValue() == null || fecha_fin.getValue() == null) {
            Alert a= new Alert(Alert.AlertType.ERROR, "Rellena todos los campos", ButtonType.OK);
            a.show();
            return false;
        }
        else if (SessionManager.getInstance().getAuxMonitorList().isEmpty()){
            Alert a= new Alert(Alert.AlertType.ERROR, "No se han seleccionado monitores", ButtonType.OK);
            a.show();
        }
        else{
            Date fechaInicio = Date.valueOf(fecha_inicio.getValue());
            Date fechaFin = Date.valueOf(fecha_fin.getValue());
            Event event=new Event(nombre_actividad.getText(), fechaInicio, fechaFin, lugar_actividad.getText(), desc_field.getText(), max_participantes.getValue());
            EventDAOImpl eventDAO = new EventDAOImpl();
            AssistanceMonitorsDAO assistanceMonitorsDAO = new AssistanceMonitorsDAO();
            //TODO: añadir monitores
            if (eventDAO.create(event)){
                int idActividad = eventDAO.getLast().getId();
                for (String i : SessionManager.getInstance().getAuxMonitorList()){
                    try{
                        assistanceMonitorsDAO.create(i, idActividad);
                    }catch (Exception e){
                        Alert a= new Alert(Alert.AlertType.ERROR, "Error al añadir monitores", ButtonType.OK);
                        a.show();
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }
            else{
                Alert a= new Alert(Alert.AlertType.ERROR, "Error al crear el evento", ButtonType.OK);
                a.show();
            }
        }
        return false;
    }

}
