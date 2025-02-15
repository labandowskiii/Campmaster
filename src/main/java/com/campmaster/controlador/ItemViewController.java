package com.campmaster.controlador;

import com.campmaster.modelo.Daos.Implementations.ItemDAOImpl;
import com.campmaster.modelo.Entities.Item;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ItemViewController {
    @FXML
    private TextField itemMarca;

    @FXML
    private TextField itemName;

    @FXML
    private Spinner<Integer> itemQty;

    @FXML
    private Button confirmButton;

    @FXML
    private void initialize() {
        initialize(null);
    }

    public void initialize(Item item) {
        if (item != null) {
            itemMarca.setText(item.getMarca());
            itemName.setText(item.getNombre());
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, item.getCantidad());
            itemQty.setValueFactory(valueFactory);
            confirmButton.setText("Actualizar");
            confirmButton.setOnAction(event -> {
                item.setId(item.getId());
                item.setMarca(itemMarca.getText());
                item.setNombre(itemName.getText());
                item.setCantidad(itemQty.getValue());
                ItemDAOImpl itemDAO = new ItemDAOImpl();
                if (!itemDAO.update(item)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al actualizar el item", ButtonType.OK);
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item actualizado correctamente", ButtonType.OK);
                    alert.show();
                }
            });
        }
        else {
            itemMarca.setText("");
            itemName.setText("");
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
            itemQty.setValueFactory(valueFactory);
            confirmButton.setText("Crear");
            confirmButton.setOnAction(event -> {
                Item newItem = new Item();
                newItem.setMarca(itemMarca.getText());
                newItem.setNombre(itemName.getText());
                newItem.setCantidad(itemQty.getValue());
                ItemDAOImpl itemDAO = new ItemDAOImpl();
                if (!itemDAO.create(newItem)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al crear el item", ButtonType.OK);
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item creado correctamente", ButtonType.OK);
                    alert.show();
                }
            });
        }
    }
}
