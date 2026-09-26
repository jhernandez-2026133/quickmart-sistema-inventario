/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.util.List;
import org.quickmartkinal.system.model.Producto;

/**
 *
 * @author informatica
 */
public interface ProductoInterface {

    boolean crear(Producto producto);

    List<Producto> listar();

    boolean editar(Producto producto);

    boolean eliminar(String idProducto);

    /**
     * Registra una entrada o salida de stock (THU 3.6 / THU 3.7).
     * tipoMovimiento debe ser "ENTRADA" o "SALIDA".
     */
    boolean registrarMovimientoStock(String idProducto, String tipoMovimiento, int cantidad);

}
