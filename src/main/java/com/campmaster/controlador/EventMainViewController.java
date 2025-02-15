package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.BookingDAOImpl;
import com.campmaster.modelo.Daos.Implementations.EventDAOImpl;
import com.campmaster.modelo.connections.StorageConnection;
import com.campmaster.modelo.Entities.Booking;
import com.campmaster.modelo.Entities.Event;

import com.campmaster.modelo.extra.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EventMainViewController {

    @FXML
    private TextArea descripcionText;

    @FXML
    private Label fechasActividad;

    @FXML
    private Button inscripcionButton;

    @FXML
    private Label lugarActividad;

    @FXML
    private Label nombreActividad;

    @FXML
    private Label nombreActividad1;

    @FXML
    private Label nombreActividad11;

    @FXML
    private Label nombreActividad111;

    @FXML
    private Button editEventButton;


    Event event;

    @FXML
    public void initialize(Event event) {
        // TODO que aparezcan los eventos
        setEvento(event);
        nombreActividad.setText(event.getNombre());
        lugarActividad.setText(event.getLugar());
        if (event.getFecha_inicio().equals(event.getFecha_fin()))
            fechasActividad.setText("El "+ event.normalizaFecha(event.getFecha_inicio()));
        else{
            fechasActividad.setText("Del "+ event.normalizaFecha(event.getFecha_inicio())+" al "+ event.normalizaFecha(event.getFecha_fin()));

        }

        if (!SessionManager.getInstance().validatePermission("Participante") || (isParticipantRegistered())){
            inscripcionButton.setDisable(true);
        }
        descripcionText.setText(event.getDescripcion());
        inscripcionButton.setOnAction(e -> {
            EventDAOImpl eventDAO = new EventDAOImpl();
            BookingDAOImpl bookingDAO = new BookingDAOImpl();
            try {
                if(eventDAO.getEventBookings(event.getId())< event.getMax_participantes()) {
                    Booking booking = new Booking(SessionManager.getInstance().getUser().getDni(), event.getId());
                    bookingDAO.create(booking);
                    ButtonType downloadButton = new ButtonType("Descargar autorización");
                    Alert a = new Alert(AlertType.CONFIRMATION, "Te has inscrito en el evento correctamente. Recuerda llevar la autorización firmada", ButtonType.OK, downloadButton);
                    a.showAndWait().ifPresent(response -> {
                        if (response == downloadButton) {
                            // TODO descargar autorización
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Guardar autorización");
                            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                            fileChooser.setInitialFileName("autorizacion.pdf");
                            fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));
                            String path = fileChooser.showSaveDialog(null).getAbsolutePath();
                            StorageConnection s = new StorageConnection();
                            try {
                                s.downloadAuth(path);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    });
                }
                else{
                    Alert a = new Alert(AlertType.ERROR, "Lo sentimos, no te has podido inscribir porque se ha alcanzado el número máximo de plazas disponibles para este evento.", ButtonType.OK );
                    a.show();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        if (SessionManager.getInstance().validatePermission("Administrador")) {
            editEventButton.setVisible(true);
            editEventButton.setDisable(false);
            editEventButton.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/campmaster/resources/EventEditView.fxml"));
                    loader.load();
                    EventEditController controller = loader.getController();
                    controller.initializeWithParams(event);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.getRoot()));
                    stage.setTitle("Editar evento");
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        
    }

    public Event getEvento() {
        return event;
    }

    public void setEvento(Event event) {
        this.event = event;
    }

    private boolean isParticipantRegistered(){
        EventDAOImpl edao=new EventDAOImpl();
        return edao.isUserRegistered();
    }

}