/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

/**
 * Representa una linea (un producto) dentro del comprobante de venta.
 *
 * @author informatica
 */
public class DetalleComprobante {

    private String producto;
    private String categoria;
    private String cantidad;
    private String precioUnitario;
    private String subtotal;

    public DetalleComprobante() {
    }

    public DetalleComprobante(String producto, String categoria, String cantidad, String precioUnitario, String subtotal) {
        this.producto = producto;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

}

