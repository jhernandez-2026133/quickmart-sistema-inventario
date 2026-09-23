/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.quickmartkinal.system.config.ConexionDB;
import org.quickmartkinal.system.model.ComprobanteItem;

/**
 *
 * @author informatica
 */
public class VentaRepository implements VentaInterface {

    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public String crearVenta(String idUsuario, double total) {
        String idVentaGenerada = null;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_create_venta(?,?,?)}")) {
            callSP.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            callSP.setDouble(2, total);
            callSP.setString(3, idUsuario);
            callSP.execute();

            // sp_create_venta no devuelve el id (sigue el mismo patron que el
            // resto de procedimientos "create"), asi que se busca la venta
            // recien insertada de este usuario.
            String sql = "select id_venta from Venta where id_user = ? order by fecha desc limit 1";
            try (PreparedStatement ps = conexionDB.getConnection().prepareStatement(sql)) {
                ps.setString(1, idUsuario);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        idVentaGenerada = resultSet.getString("id_venta");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la venta: " + e.getMessage());
        }
        return idVentaGenerada;
    }

    @Override
    public boolean crearDetalleVenta(String idVenta, String idProducto, int cantidad, double precioUnitario, double subtotal) {
        boolean creado = false;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_create_detalle_venta(?,?,?,?,?)}")) {
            callSP.setInt(1, cantidad);
            callSP.setDouble(2, precioUnitario);
            callSP.setDouble(3, subtotal);
            callSP.setString(4, idVenta);
            callSP.setString(5, idProducto);
            callSP.execute();
            creado = true;
        } catch (SQLException e) {
            System.out.println("Error al crear el detalle de venta: " + e.getMessage());
        }
        return creado;
    }

    @Override
    public List<ComprobanteItem> obtenerComprobante(String idVenta) {
        List<ComprobanteItem> items = new ArrayList<>();
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_comprobante_venta(?)}")) {
            callSP.setString(1, idVenta);
            boolean hayResultados = callSP.execute();

            if (hayResultados) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        ComprobanteItem item = new ComprobanteItem(
                                resultSet.getString("ID Venta"),
                                resultSet.getString("Fecha"),
                                resultSet.getString("Cliente"),
                                resultSet.getString("Producto"),
                                resultSet.getString("Categoria"),
                                resultSet.getInt("Cantidad"),
                                resultSet.getDouble("Precio Unitario"),
                                resultSet.getDouble("Subtotal")
                        );
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el comprobante: " + e.getMessage());
        }
        return items;
    }

    @Override
    public List<ComprobanteItem> obtenerInventarioVentas() {
        List<ComprobanteItem> items = new ArrayList<>();
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_inventario_ventas()}")) {
            boolean hayResultados = callSP.execute();

            if (hayResultados) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        ComprobanteItem item = new ComprobanteItem(
                                resultSet.getString("ID Venta"),
                                resultSet.getString("Fecha"),
                                resultSet.getString("Cliente"),
                                resultSet.getString("Producto"),
                                resultSet.getString("Categoria"),
                                resultSet.getInt("Cantidad"),
                                resultSet.getDouble("Precio Unitario"),
                                resultSet.getDouble("Subtotal")
                        );
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el inventario de ventas: " + e.getMessage());
        }
        return items;
    }

}
