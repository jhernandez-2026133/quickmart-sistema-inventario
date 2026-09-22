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
import org.quickmartkinal.system.model.ComprobanteVenta;
import org.quickmartkinal.system.model.DetalleComprobante;
import org.quickmartkinal.system.model.VentaResumen;

/**
 *
 * @author informatica
 */
public class ComprobanteRepository implements ComprobanteInterface {

    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public List<VentaResumen> listarVentas() {
        List<VentaResumen> ventas = new ArrayList<>();
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_mostrar_venta()}")) {
            if (callSP.execute()) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        ventas.add(new VentaResumen(
                                resultSet.getString("ID Venta"),
                                resultSet.getString("Fecha"),
                                resultSet.getString("Usuario"),
                                resultSet.getString("Total")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las ventas: " + e.getMessage());
        }
        return ventas;
    }

    @Override
    public ComprobanteVenta generarComprobante(String idVenta) {
        ComprobanteVenta comprobante = null;
        try (CallableStatement callSP = conexionDB.getConnection().prepareCall("{call sp_generar_comprobante_venta(?)}")) {
            callSP.setString(1, idVenta);

            if (callSP.execute()) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        if (comprobante == null) {
                            comprobante = new ComprobanteVenta(
                                    resultSet.getString("ID Venta"),
                                    resultSet.getString("Fecha"),
                                    resultSet.getString("Cajero"),
                                    resultSet.getString("Total Venta")
                            );
                        }
                        comprobante.getDetalles().add(new DetalleComprobante(
                                resultSet.getString("Producto"),
                                resultSet.getString("Categoria"),
                                resultSet.getString("Cantidad"),
                                resultSet.getString("Precio Unitario"),
                                resultSet.getString("Subtotal")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al generar el comprobante: " + e.getMessage());
        }
        return comprobante;
    }

}

