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

}
