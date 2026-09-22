/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el comprobante completo de una venta: su encabezado y el
 * detalle de los productos vendidos.
 *
 * @author informatica
 */
public class ComprobanteVenta {

    private String idVenta;
    private String fecha;
    private String cajero;
    private String total;
    private final List<DetalleComprobante> detalles = new ArrayList<>();

    public ComprobanteVenta() {
    }

    public ComprobanteVenta(String idVenta, String fecha, String cajero, String total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.cajero = cajero;
        this.total = total;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DetalleComprobante> getDetalles() {
        return detalles;
    }

}
