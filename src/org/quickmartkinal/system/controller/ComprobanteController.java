/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.quickmartkinal.system.model.ComprobanteItem;
import org.quickmartkinal.system.utils.AlertInformation;

public class ComprobanteController implements Initializable {

    @FXML
    private VBox panelComprobante;
    @FXML
    private Label lblIdVenta;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCliente;
    @FXML
    private TableView<ComprobanteItem> tblComprobante;
    @FXML
    private TableColumn<ComprobanteItem, String> colProducto;
    @FXML
    private TableColumn<ComprobanteItem, String> colCategoria;
    @FXML
    private TableColumn<ComprobanteItem, Integer> colCantidad;
    @FXML
    private TableColumn<ComprobanteItem, Double> colPrecioUnitario;
    @FXML
    private TableColumn<ComprobanteItem, Double> colSubtotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnCerrar;

    private final AlertInformation alertInfo = new AlertInformation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    public void cargarComprobante(List<ComprobanteItem> renglones) {
        if (renglones.isEmpty()) {
            return;
        }

        ComprobanteItem primero = renglones.get(0);
        lblIdVenta.setText("Venta: " + primero.getIdVenta());
        lblFecha.setText("Fecha: " + primero.getFecha());
        lblCliente.setText("Cliente: " + primero.getCliente());

        ObservableList<ComprobanteItem> items = FXCollections.observableArrayList(renglones);
        tblComprobante.setItems(items);

        double total = 0;
        for (ComprobanteItem renglon : renglones) {
            total += renglon.getSubtotal();
        }
        lblTotal.setText(String.format("Total: Q%.2f", total));
    }

    @FXML
    public void onCerrar(MouseEvent event) {
        Stage stage = (Stage) panelComprobante.getScene().getWindow();
        stage.close();
    }

}
