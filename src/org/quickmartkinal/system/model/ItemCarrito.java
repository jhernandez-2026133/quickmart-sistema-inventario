/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

/**
 * Representa un renglon del carrito de compras del Cliente. Vive solo en
 * memoria mientras tanto: todavia no se guarda en la base de datos porque
 * las tablas de venta/detalle_venta son del Sprint 3.
 *
 * @author informatica
 */
public class ItemCarrito {

    private Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return producto.getNombre();
    }

    public double getSubtotal() {
        return cantidad * producto.getPrecioVenta();
    }

}
