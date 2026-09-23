/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.service;

import java.util.List;
import org.quickmartkinal.system.model.ComprobanteItem;
import org.quickmartkinal.system.model.ItemCarrito;
import org.quickmartkinal.system.repository.ProductoRepository;
import org.quickmartkinal.system.repository.VentaRepository;

/**
 *
 * @author informatica
 */
public class VentaService {

    private static final String SALIDA = "SALIDA";

    private final VentaRepository ventaRepository = new VentaRepository();
    private final ProductoRepository productoRepository = new ProductoRepository();

    private String ultimoIdVenta;

    public VentaStatus registrarVenta(List<ItemCarrito> carrito, String idUsuario) {
        boolean stockSuficiente = validarStock(carrito);
        if (stockSuficiente == false) {
            return VentaStatus.STOCK_INSUFICIENTE;
        }

        double total = 0;
        for (ItemCarrito item : carrito) {
            total += item.getSubtotal();
        }

        String idVenta = ventaRepository.crearVenta(idUsuario, total);
        if (idVenta == null) {
            return VentaStatus.OPERACION_FALLIDA;
        }

        for (ItemCarrito item : carrito) {
            boolean detalleCreado = ventaRepository.crearDetalleVenta(
                    idVenta,
                    item.getProducto().getIdProducto(),
                    item.getCantidad(),
                    item.getProducto().getPrecioVenta(),
                    item.getSubtotal());

            boolean stockDescontado = productoRepository.registrarMovimientoStock(
                    item.getProducto().getIdProducto(), SALIDA, item.getCantidad());

            if (detalleCreado == false || stockDescontado == false) {
                return VentaStatus.OPERACION_FALLIDA;
            }
        }

        ultimoIdVenta = idVenta;
        return VentaStatus.VENTA_REGISTRADA;
    }

    /**
     * Revisa en memoria que ningun renglon del carrito pida mas cantidad de
     * la que hay disponible, antes de tocar la base de datos.
     */
    private boolean validarStock(List<ItemCarrito> carrito) {
        for (ItemCarrito item : carrito) {
            if (item.getCantidad() > item.getProducto().getStock()) {
                return false;
            }
        }
        return true;
    }

    public String getUltimoIdVenta() {
        return ultimoIdVenta;
    }

    public List<ComprobanteItem> obtenerComprobante(String idVenta) {
        return ventaRepository.obtenerComprobante(idVenta);
    }

    /**
     * THU: el Gerente consulta aqui el inventario de ventas (todas las
     * ventas registradas, con el mismo detalle que un comprobante).
     */
    public List<ComprobanteItem> obtenerInventarioVentas() {
        return ventaRepository.obtenerInventarioVentas();
    }

}
