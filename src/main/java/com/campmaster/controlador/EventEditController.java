package com.campmaster.controlador;
import com.campmaster.AppCampMaster;
import com.campmaster.modelo.Daos.Implementations.AssistanceMonitorsDAO;
import com.campmaster.modelo.Daos.Implementations.BookingDAOImpl;
import com.campmaster.modelo.Daos.Implementations.EventDAOImpl;
import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;
import com.campmaster.modelo.Entities.Event;
import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class EventEditController {


    @FXML
    private Button confirmar_button;

    @FXML
    private Button eliminar_button;

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

    Event event=null;

    @FXML
    private void initialize(){

    }

    @FXML
    public void initializeWithParams(Event evento) {
        UserDAOImpl userDAO = new UserDAOImpl();
        SessionManager.getInstance().setAuxMonitorList(userDAO.getEventMonitors(evento.getId()));

        confirmar_button.setOnAction(e -> {
            if (!editEvent()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al modificar el evento", ButtonType.OK);
                alert.show();
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Evento modificado correctamente", ButtonType.OK);
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
                controller.initializeForEdit(userDAO.getEventMonitors(evento.getId()));
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

        eliminar_button.setOnAction(e->{
            try{
                EventDAOImpl eventDAO=new EventDAOImpl();
                if (eventDAO.getEventBookings(event.getId())>0){
                    BookingDAOImpl bookingDAO=new BookingDAOImpl();
                    bookingDAO.deleteAllActivityBookings(event.getId());
                }
                if (!eventDAO.delete(this.event)){
                    Alert a=new Alert(Alert.AlertType.ERROR, "No se ha podido eliminar la actividad", ButtonType.OK);
                    a.show();
                }
                else{
                    Alert a=new Alert(Alert.AlertType.CONFIRMATION, "Evento eliminado correctamente", ButtonType.OK);
                    a.show();
                }

            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, evento.getMax_participantes());
        max_participantes.setValueFactory(valueFactory);
        this.event = evento;
        setEventFields();
    }


    private boolean editEvent() {
        if (nombre_actividad.getText().isEmpty() || lugar_actividad.getText().isEmpty() || fecha_inicio.getValue() == null || fecha_fin.getValue() == null) {
            Alert a= new Alert(Alert.AlertType.ERROR, "Rellena todos los campos", ButtonType.OK);
            a.show();
        }
        else if (SessionManager.getInstance().getAuxMonitorList() == null || SessionManager.getInstance().getAuxMonitorList().isEmpty()){
            Alert a= new Alert(Alert.AlertType.ERROR, "No se han seleccionado monitores", ButtonType.OK);
            a.show();
        }
        else{
            Date fechaInicio = Date.valueOf(fecha_inicio.getValue());
            Date fechaFin = Date.valueOf(fecha_fin.getValue());
            Event event=new Event(this.event.getId(), nombre_actividad.getText(), fechaInicio, fechaFin, lugar_actividad.getText(), desc_field.getText(), max_participantes.getValue());
            if (event.validate()){
                Alert a= new Alert(Alert.AlertType.ERROR, "El evento no está formado correctamente", ButtonType.OK);
                a.show();
                return false;
            }
            EventDAOImpl eventDAO = new EventDAOImpl();
            AssistanceMonitorsDAO assistanceMonitorsDAO = new AssistanceMonitorsDAO();
            if (eventDAO.update(event)){
                int idActividad = eventDAO.getLast().getId();
                assistanceMonitorsDAO.deleteAll(event.getId());
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

    private void setEventFields(){
        nombre_actividad.setText(event.getNombre());
        lugar_actividad.setText(event.getLugar());
        fecha_inicio.setValue(event.getFecha_inicio().toLocalDate());
        fecha_fin.setValue(event.getFecha_fin().toLocalDate());
        desc_field.setText(event.getDescripcion());
        AssistanceMonitorsDAO monitorsDAO = new AssistanceMonitorsDAO();
        num_monit.setText("Monitores seleccionados: " + monitorsDAO.getMonitorFromActivity(event.getId()).size());
    }

}
