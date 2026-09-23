/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

/**
 * Representa un renglon del comprobante de venta, tal como lo devuelve
 * sp_comprobante_venta (venta + detalle_venta + producto + categoria).
 *
 * @author informatica
 */
public class ComprobanteItem {

    private String idVenta;
    private String fecha;
    private String cliente;
    private String producto;
    private String categoria;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ComprobanteItem(String idVenta, String fecha, String cliente, String producto, String categoria,
            int cantidad, double precioUnitario, double subtotal) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.cliente = cliente;
        this.producto = producto;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getProducto() {
        return producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

}
