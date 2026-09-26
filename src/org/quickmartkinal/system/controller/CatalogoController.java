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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.quickmartkinal.system.model.Categoria;
import org.quickmartkinal.system.model.ComprobanteItem;
import org.quickmartkinal.system.model.ItemCarrito;
import org.quickmartkinal.system.model.Producto;
import org.quickmartkinal.system.model.Usuario;
import org.quickmartkinal.system.service.CatalogoService;
import org.quickmartkinal.system.service.CatalogoStatus;
import org.quickmartkinal.system.service.VentaService;
import org.quickmartkinal.system.service.VentaStatus;
import org.quickmartkinal.system.utils.AlertInformation;
import org.quickmartkinal.system.utils.Sesion;
import org.quickmartkinal.system.utils.Validations;
import org.quickmartkinal.system.utils.ViewFactory;

public class CatalogoController implements Initializable {

    private static final String ROL_GERENTE = "Gerente";
    private static final String ROL_CLIENTE = "Cliente";
    private static final String ROL_BODEGUERO = "Bodeguero";

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
    private Label lblCodigo;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private Label lblCategoria;
    @FXML
    private ComboBox<Categoria> cmbCategoria;
    @FXML
    private Label lblStock;
    @FXML
    private TextField txtStock;
    @FXML
    private Label lblCosto;
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

    @FXML
    private VBox panelCarrito;
    @FXML
    private TableView<ItemCarrito> tblCarrito;
    @FXML
    private TableColumn<ItemCarrito, String> colCarritoProducto;
    @FXML
    private TableColumn<ItemCarrito, Integer> colCarritoCantidad;
    @FXML
    private TableColumn<ItemCarrito, Double> colCarritoSubtotal;
    @FXML
    private Label lblTotalCarrito;
    @FXML
    private Button btnQuitarDelCarrito;
    @FXML
    private Button btnFinalizarCompra;

    @FXML
    private Button btnEntrada;
    @FXML
    private Button btnSalida;
    @FXML
    private Button btnVerInventarioVentas;

    private final CatalogoService catalogoService = new CatalogoService();
    private final VentaService ventaService = new VentaService();
    private final Validations validate = new Validations();
    private final AlertInformation alertInfo = new AlertInformation();
    private final ObservableList<ItemCarrito> carrito = FXCollections.observableArrayList();

    private Producto productoSeleccionado;
    private Usuario usuarioActual;
    private boolean esCliente = false;
    private boolean esBodeguero = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        tblProductos.setOnMouseClicked((MouseEvent event) -> onSeleccionarFila());

        usuarioActual = Sesion.getInstanciaSesion().getUsuarioActual();
        if (usuarioActual != null) {
            lblBienvenida.setText("Bienvenido, " + usuarioActual.getNombreUsuario());
            lblRol.setText("Rol: " + usuarioActual.getNombreRol());
        }

        String rolActual = usuarioActual != null ? usuarioActual.getNombreRol() : "";
        boolean esGerente = ROL_GERENTE.equalsIgnoreCase(rolActual);
        esCliente = ROL_CLIENTE.equalsIgnoreCase(rolActual);
        esBodeguero = ROL_BODEGUERO.equalsIgnoreCase(rolActual);

        // el Gerente solo puede consultar el catalogo, no editarlo,
        // y ademas puede consultar el inventario de ventas del negocio.
        if (esGerente) {
            txtCodigo.setDisable(true);
            txtNombre.setDisable(true);
            cmbCategoria.setDisable(true);
            txtStock.setDisable(true);
            txtCosto.setDisable(true);
            txtPrecioVenta.setDisable(true);
            ocultar(btnGuardar, btnEditar, btnEliminar, btnLimpiar);

            btnVerInventarioVentas.setVisible(true);
            btnVerInventarioVentas.setManaged(true);
        }

        // El Cliente no administra el catalogo: solo ve producto/categoria/precio,
        // usa el campo "Stock" como cantidad a comprar, y "Guardar" agrega al carrito.
        if (esCliente) {
            ocultar(lblCodigo, txtCodigo, lblCosto, txtCosto, lblCategoria, cmbCategoria,
                    btnEditar, btnEliminar);
            colCosto.setVisible(false);
            txtNombre.setEditable(false);
            txtPrecioVenta.setEditable(false);
            lblStock.setText("Cantidad a comprar");
            btnGuardar.setText("Agregar al carrito");

            panelCarrito.setVisible(true);
            panelCarrito.setManaged(true);
            configurarTablaCarrito();
        }

        // el Bodeguero no administra precios ni categorias, solo
        // registra entradas/salidas de stock del producto seleccionado.
        if (esBodeguero) {
            ocultar(lblCodigo, txtCodigo, lblCosto, txtCosto, txtPrecioVenta, lblCategoria, cmbCategoria,
                    btnGuardar, btnEditar, btnEliminar);
            txtNombre.setEditable(false);
            lblStock.setText("Cantidad del movimiento");

            btnEntrada.setVisible(true);
            btnEntrada.setManaged(true);
            btnSalida.setVisible(true);
            btnSalida.setManaged(true);
        }

        cargarCategorias();
        cargarProductos();
    }

    private void ocultar(javafx.scene.Node... nodos) {
        for (javafx.scene.Node nodo : nodos) {
            nodo.setVisible(false);
            nodo.setManaged(false);
        }
    }

    private void configurarTablaCarrito() {
        colCarritoProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colCarritoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCarritoSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tblCarrito.setItems(carrito);
        actualizarTotalCarrito();
    }

    private void actualizarTotalCarrito() {
        double total = 0;
        for (ItemCarrito item : carrito) {
            total += item.getSubtotal();
        }
        lblTotalCarrito.setText(String.format("Total: Q%.2f", total));
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
        txtStock.setText(esCliente ? "1" : String.valueOf(productoSeleccionado.getStock()));
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
        if (esCliente) {
            onAgregarAlCarrito();
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
        CatalogoStatus status = catalogoService.crearProducto(producto);
        mostrarResultado(status);
    }

    private void onAgregarAlCarrito() {
        if (productoSeleccionado == null) {
            alertInfo.viewAlert("ERROR", "SIN SELECCION", "ERROR", "Selecciona un producto de la tabla para agregarlo al carrito.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            alertInfo.viewAlert("ERROR", "CANTIDAD INVALIDA", "ERROR DE FORMATO", "La cantidad a comprar debe ser un numero.");
            return;
        }

        if (cantidad <= 0) {
            alertInfo.viewAlert("ERROR", "CANTIDAD INVALIDA", "ERROR", "La cantidad a comprar debe ser mayor a 0.");
            return;
        }
        if (cantidad > productoSeleccionado.getStock()) {
            alertInfo.viewAlert("ERROR", "STOCK INSUFICIENTE", "ERROR", "Solo hay " + productoSeleccionado.getStock() + " unidades disponibles.");
            return;
        }

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getIdProducto().equals(productoSeleccionado.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                tblCarrito.refresh();
                actualizarTotalCarrito();
                limpiarFormulario();
                return;
            }
        }

        carrito.add(new ItemCarrito(productoSeleccionado, cantidad));
        actualizarTotalCarrito();
        limpiarFormulario();
    }

    @FXML
    public void onQuitarDelCarrito(MouseEvent event) {
        ItemCarrito seleccionado = tblCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alertInfo.viewAlert("ERROR", "SIN SELECCION", "ERROR", "Selecciona un producto del carrito para quitarlo.");
            return;
        }
        carrito.remove(seleccionado);
        actualizarTotalCarrito();
    }

    @FXML
    public void onFinalizarCompra(MouseEvent event) {
        if (carrito.isEmpty()) {
            alertInfo.viewAlert("ERROR", "CARRITO VACIO", "ERROR", "Agrega al menos un producto antes de finalizar la compra.");
            return;
        }
        if (usuarioActual == null || usuarioActual.getIdUsuario() == null) {
            alertInfo.viewAlert("ERROR", "SESION INVALIDA", "ERROR", "No se encontro tu sesion. Vuelve a iniciar sesion.");
            return;
        }

        VentaStatus status = ventaService.registrarVenta(carrito, usuarioActual.getIdUsuario());

        switch (status) {
            case STOCK_INSUFICIENTE ->
                alertInfo.viewAlert("ERROR", "STOCK INSUFICIENTE", "NO SE REGISTRO LA COMPRA",
                        "Alguno de los productos ya no tiene suficiente stock disponible. Revisa tu carrito.");
            case OPERACION_FALLIDA ->
                alertInfo.viewAlert("ERROR", "ERROR AL REGISTRAR", "OCURRIO UN ERROR",
                        "No se pudo registrar la compra. Intenta nuevamente.");
            case VENTA_REGISTRADA ->
                generarYAbrirComprobante(ventaService.getUltimoIdVenta());
            default -> {
            }
        }
    }

    private void generarYAbrirComprobante(String idVenta) {
        List<ComprobanteItem> renglones = ventaService.obtenerComprobante(idVenta);

        ViewFactory viewFactory = new ViewFactory();
        viewFactory.viewComprobante(renglones);

        carrito.clear();
        actualizarTotalCarrito();
        cargarProductos();
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

    @FXML
    public void onVerInventarioVentas(MouseEvent event) {
        List<ComprobanteItem> renglones = ventaService.obtenerInventarioVentas();
        if (renglones.isEmpty()) {
            alertInfo.viewAlert("INFORMATION", "SIN VENTAS", "INVENTARIO DE VENTAS",
                    "Todavia no hay ventas registradas en el sistema.");
            return;
        }

        ViewFactory viewFactory = new ViewFactory();
        viewFactory.viewInventarioVentas(renglones);
    }

    @FXML
    public void onEntrada(MouseEvent event) {
        registrarMovimiento("ENTRADA");
    }

    @FXML
    public void onSalida(MouseEvent event) {
        registrarMovimiento("SALIDA");
    }

    private void registrarMovimiento(String tipoMovimiento) {
        if (productoSeleccionado == null) {
            alertInfo.viewAlert("ERROR", "SIN SELECCION", "ERROR", "Selecciona un producto de la tabla.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            alertInfo.viewAlert("ERROR", "CANTIDAD INVALIDA", "ERROR DE FORMATO", "La cantidad debe ser un numero.");
            return;
        }

        CatalogoStatus status = catalogoService.registrarMovimientoStock(
                productoSeleccionado.getIdProducto(), tipoMovimiento, cantidad);

        switch (status) {
            case STOCK_INSUFICIENTE ->
                alertInfo.viewAlert("ERROR", "STOCK INSUFICIENTE", "NO SE REGISTRO EL MOVIMIENTO",
                        "No hay suficiente stock disponible para esa salida.");
            case OPERACION_FALLIDA ->
                alertInfo.viewAlert("ERROR", "ERROR AL REGISTRAR", "OCURRIO UN ERROR",
                        "No se pudo registrar el movimiento. Intenta nuevamente.");
            case MOVIMIENTO_REGISTRADO -> {
                alertInfo.viewAlert("INFORMATION", "MOVIMIENTO REGISTRADO", "STOCK ACTUALIZADO",
                        "El movimiento de " + tipoMovimiento.toLowerCase() + " se registro correctamente.");
                limpiarFormulario();
                cargarProductos();
            }
            default -> {
            }
        }
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
