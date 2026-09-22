/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.quickmartkinal.system.repository;

import java.util.List;
import org.quickmartkinal.system.model.ComprobanteVenta;
import org.quickmartkinal.system.model.VentaResumen;

/**
 *
 * @author informatica
 */
public interface ComprobanteInterface {

    List<VentaResumen> listarVentas();

    ComprobanteVenta generarComprobante(String idVenta);

}

