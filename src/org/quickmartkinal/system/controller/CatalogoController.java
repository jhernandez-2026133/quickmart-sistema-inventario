/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.quickmartkinal.system.model.Categoria;
import org.quickmartkinal.system.model.Producto;
import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.service.CatalogoService;
import org.quickmartkinal.system.service.CatalogoStatus;
import org.quickmartkinal.system.utils.AlertInformation;
import org.quickmartkinal.system.utils.Sesion;
import org.quickmartkinal.system.utils.Validations;
import org.quickmartkinal.system.utils.ViewFactory;

public class CatalogoController implements Initializable {

    private static final String ROL_GERENTE = "Gerente";

    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblRol;
    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, Double> colCosto;
    @FXML
    private TableColumn<Producto, Double> colPrecioVenta;

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<Categoria> cmbCategoria;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtCosto;
    @FXML
    private TextField txtPrecioVenta;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    private final CatalogoService catalogoService = new CatalogoService();
    private final Validations validate = new Validations();
    private final AlertInformation alertInfo = new AlertInformation();

    private Producto productoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        tblProductos.setOnMouseClicked((MouseEvent event) -> onSeleccionarFila());

        Usuario usuarioActual = Sesion.getInstanciaSesion().getUsuarioActual();
        if (usuarioActual != null) {
            lblBienvenida.setText("Bienvenido, " + usuarioActual.getNombreUsuario());
            lblRol.setText("Rol: " + usuarioActual.getNombreRol());
        }

        // THU 3.5: el Gerente solo puede consultar el catalogo, no editarlo.
        boolean esGerente = usuarioActual != null && ROL_GERENTE.equals(usuarioActual.getNombreRol());
        if (esGerente) {
            txtCodigo.setDisable(true);
            txtNombre.setDisable(true);
            cmbCategoria.setDisable(true);
            txtStock.setDisable(true);
            txtCosto.setDisable(true);
            txtPrecioVenta.setDisable(true);
            btnGuardar.setVisible(false);
            btnGuardar.setManaged(false);
            btnEditar.setVisible(false);
            btnEditar.setManaged(false);
            btnEliminar.setVisible(false);
            btnEliminar.setManaged(false);
            btnLimpiar.setVisible(false);
            btnLimpiar.setManaged(false);
        }

        cargarCategorias();
        cargarProductos();
    }

    private void cargarCategorias() {
        ObservableList<Categoria> categorias = FXCollections.observableArrayList(catalogoService.listarCategorias());
        cmbCategoria.setItems(categorias);
    }

    private void cargarProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList(catalogoService.listarProductos());
        tblProductos.setItems(productos);
    }

    private void onSeleccionarFila() {
        productoSeleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            return;
        }
        txtCodigo.setText(productoSeleccionado.getCodigoBarras());
        txtNombre.setText(productoSeleccionado.getNombre());
        txtStock.setText(String.valueOf(productoSeleccionado.getStock()));
        txtCosto.setText(String.valueOf(productoSeleccionado.getPrecioCosto()));
        txtPrecioVenta.setText(String.valueOf(productoSeleccionado.getPrecioVenta()));

        for (Categoria categoria : cmbCategoria.getItems()) {
            if (categoria.getNombreCategoria().equals(productoSeleccionado.getNombreCategoria())) {
                cmbCategoria.getSelectionModel().select(categoria);
            }
        }
    }

    private boolean camposValidos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String stock = txtStock.getText().trim();
        String costo = txtCosto.getText().trim();
        String precioVenta = txtPrecioVenta.getText().trim();

        if (validate.validateTextEmpty(codigo) || validate.validateTextEmpty(nombre)
                || validate.validateTextEmpty(stock) || validate.validateTextEmpty(costo)
                || validate.validateTextEmpty(precioVenta) || cmbCategoria.getValue() == null) {
            alertInfo.viewAlert("ERROR", "CAMPOS VACIOS", "ERROR DE CAMPOS", "Debes llenar todos los campos y elegir una categoria.");
            return false;
        }
        return true;
    }

    private Producto construirProductoDesdeFormulario() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        int stock = Integer.parseInt(txtStock.getText().trim());
        double costo = Double.parseDouble(txtCosto.getText().trim());
        double precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
        Categoria categoria = cmbCategoria.getValue();

        Producto producto = new Producto(codigo, nombre, categoria.getIdCategoria(), stock, costo, precioVenta);
        producto.setNombreCategoria(categoria.getNombreCategoria());
        return producto;
    }

    @FXML
    public void onGuardar(MouseEvent event) {
        if (camposValidos() == false) {
            return;
        }

        Producto producto;
        try {
            producto = construirProductoDesdeFormulario();
        } catch (NumberFormatException e) {
            alertInfo.viewAlert("ERROR", "DATOS INVALIDOS", "ERROR DE FORMATO", "Stock, costo y precio de venta deben ser numeros.");
            return;
        }

        CatalogoStatus status = catalogoService.crearProducto(producto);
        mostrarResultado(status);
    }

    @FXML
    public void onEditar(MouseEvent event) {
        if (productoSeleccionado == null) {
            alertInfo.viewAlert("ERROR", "SIN SELECCION", "ERROR", "Selecciona un producto de la tabla para editarlo.");
            return;
        }
        if (camposValidos() == false) {
            return;
        }

        Producto producto;
        try {
            producto = construirProductoDesdeFormulario();
        } catch (NumberFormatException e) {
            alertInfo.viewAlert("ERROR", "DATOS INVALIDOS", "ERROR DE FORMATO", "Stock, costo y precio de venta deben ser numeros.");
            return;
        }
        producto.setIdProducto(productoSeleccionado.getIdProducto());

        CatalogoStatus status = catalogoService.editarProducto(producto);
        mostrarResultado(status);
    }

    @FXML
    public void onEliminar(MouseEvent event) {
        if (productoSeleccionado == null) {
            alertInfo.viewAlert("ERROR", "SIN SELECCION", "ERROR", "Selecciona un producto de la tabla para eliminarlo.");
            return;
        }

        CatalogoStatus status = catalogoService.eliminarProducto(productoSeleccionado.getIdProducto());
        mostrarResultado(status);
    }

    @FXML
    public void onLimpiar(MouseEvent event) {
        limpiarFormulario();
    }

    private void mostrarResultado(CatalogoStatus status) {
        switch (status) {
            case PRECIO_INVALIDO ->
                alertInfo.viewAlert("ERROR", "PRECIO INVALIDO", "NO SE GUARDO EL PRODUCTO",
                        "El precio de venta debe ser mayor al costo del producto.");
            case CODIGO_DUPLICADO ->
                alertInfo.viewAlert("ERROR", "CODIGO DUPLICADO", "NO SE GUARDO EL PRODUCTO",
                        "Ya existe un producto registrado con ese codigo de barras.");
            case OPERACION_FALLIDA ->
                alertInfo.viewAlert("ERROR", "ERROR AL GUARDAR", "OCURRIO UN ERROR",
                        "No se pudo completar la operacion. Intenta nuevamente.");
            case PRODUCTO_GUARDADO -> {
                alertInfo.viewAlert("INFORMATION", "PRODUCTO GUARDADO", "REGISTRO EXITOSO",
                        "El producto se registro correctamente.");
                limpiarFormulario();
                cargarProductos();
            }
            case PRODUCTO_ACTUALIZADO -> {
                alertInfo.viewAlert("INFORMATION", "PRODUCTO ACTUALIZADO", "CAMBIOS GUARDADOS",
                        "El producto se actualizo correctamente.");
                limpiarFormulario();
                cargarProductos();
            }
            case PRODUCTO_ELIMINADO -> {
                alertInfo.viewAlert("INFORMATION", "PRODUCTO ELIMINADO", "PRODUCTO ELIMINADO",
                        "El producto se elimino correctamente.");
                limpiarFormulario();
                cargarProductos();
            }
        }
    }

    private void limpiarFormulario() {
        productoSeleccionado = null;
        txtCodigo.clear();
        txtNombre.clear();
        txtStock.clear();
        txtCosto.clear();
        txtPrecioVenta.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        tblProductos.getSelectionModel().clearSelection();
    }

    @FXML
    public void onLogout(MouseEvent event) {
        Sesion.getInstanciaSesion().cerrarSesion();
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.viewLogin();
    }

}
