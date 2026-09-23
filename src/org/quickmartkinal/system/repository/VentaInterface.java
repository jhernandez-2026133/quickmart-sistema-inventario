/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.util.List;
import org.quickmartkinal.system.model.ComprobanteItem;

/**
 *
 * @author informatica
 */
public interface VentaInterface {

    String crearVenta(String idUsuario, double total);

    boolean crearDetalleVenta(String idVenta, String idProducto, int cantidad, double precioUnitario, double subtotal);

    List<ComprobanteItem> obtenerComprobante(String idVenta);

    List<ComprobanteItem> obtenerInventarioVentas();

}
