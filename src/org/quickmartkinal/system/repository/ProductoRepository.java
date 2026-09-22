/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.quickmartkinal.system.config.ConexionDB;
import org.quickmartkinal.system.model.Producto;

/**
 *
 * @author informatica
 */
public class ProductoRepository implements ProductoInterface {

    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public boolean crear(Producto producto) {
        boolean creado = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_create_producto(?,?,?,?,?,?)}")) {
            callSP.setString(1, producto.getCodigoBarras());
            callSP.setString(2, producto.getNombre());
            callSP.setInt(3, producto.getStock());
            callSP.setDouble(4, producto.getPrecioCosto());
            callSP.setDouble(5, producto.getPrecioVenta());
            callSP.setString(6, producto.getIdCategoria());

            callSP.execute();
            creado = true;
        } catch (SQLException e) {
            System.out.println("Error al crear el producto: " + e.getMessage());
        }
        return creado;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_mostrar_producto()}")) {
            boolean hayResultados = callSP.execute();

            if (hayResultados) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        Producto producto = new Producto(
                                resultSet.getString("ID Producto"),
                                resultSet.getString("Codigo"),
                                resultSet.getString("Nombre"),
                                null,
                                resultSet.getString("Categoria"),
                                resultSet.getInt("Stock"),
                                resultSet.getDouble("Costo"),
                                resultSet.getDouble("Precio Venta")
                        );
                        productos.add(producto);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public boolean editar(Producto producto) {
        boolean editado = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_editar_producto(?,?,?,?,?,?,?)}")) {
            callSP.setString(1, producto.getIdProducto());
            callSP.setString(2, producto.getCodigoBarras());
            callSP.setString(3, producto.getNombre());
            callSP.setInt(4, producto.getStock());
            callSP.setDouble(5, producto.getPrecioCosto());
            callSP.setDouble(6, producto.getPrecioVenta());
            callSP.setString(7, producto.getIdCategoria());

            callSP.execute();
            editado = true;
        } catch (SQLException e) {
            System.out.println("Error al editar el producto: " + e.getMessage());
        }
        return editado;
    }

    @Override
    public boolean eliminar(String idProducto) {
        boolean eliminado = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_eliminar_producto(?)}")) {
            callSP.setString(1, idProducto);
            callSP.execute();
            eliminado = true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
        return eliminado;
    }

}
