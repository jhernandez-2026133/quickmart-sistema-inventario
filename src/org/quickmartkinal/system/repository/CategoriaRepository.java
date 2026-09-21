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
import org.quickmartkinal.system.model.Categoria;

/**
 *
 * @author informatica
 */
public class CategoriaRepository implements CategoriaInterface {

    private CallableStatement callSP;
    private final ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();

    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            callSP = conexionDB.getConnection().prepareCall("{call sp_mostrar_categoria()}");
            boolean hayResultados = callSP.execute();

            if (hayResultados) {
                try (ResultSet resultSet = callSP.getResultSet()) {
                    while (resultSet.next()) {
                        Categoria categoria = new Categoria(
                                resultSet.getString("ID Categoria"),
                                resultSet.getString("Nombre De Categoria")
                        );
                        categorias.add(categoria);
                    }
                }
            }
            callSP.close();
        } catch (SQLException e) {
            System.out.println("Error al listar categorias");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }

}
