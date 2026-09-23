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

/**
 * THU: pantalla de solo lectura para que el Gerente consulte el inventario
 * de ventas (todas las ventas registradas en el sistema).
 *
 * @author informatica
 */
public class InventarioVentasController implements Initializable {

    @FXML
    private VBox panelInventarioVentas;
    @FXML
    private TableView<ComprobanteItem> tblInventarioVentas;
    @FXML
    private TableColumn<ComprobanteItem, String> colIdVenta;
    @FXML
    private TableColumn<ComprobanteItem, String> colFecha;
    @FXML
    private TableColumn<ComprobanteItem, String> colCliente;
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
    private Label lblTotalVentas;
    @FXML
    private Button btnCerrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    public void cargarInventarioVentas(List<ComprobanteItem> renglones) {
        ObservableList<ComprobanteItem> items = FXCollections.observableArrayList(renglones);
        tblInventarioVentas.setItems(items);

        double total = 0;
        for (ComprobanteItem renglon : renglones) {
            total += renglon.getSubtotal();
        }
        lblTotalVentas.setText(String.format("Total vendido: Q%.2f", total));
    }

    @FXML
    public void onCerrar(MouseEvent event) {
        Stage stage = (Stage) panelInventarioVentas.getScene().getWindow();
        stage.close();
    }

}
