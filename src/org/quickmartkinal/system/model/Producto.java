/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

/**
 *
 * @author informatica
 */
public class Producto {

    private String idProducto;
    private String codigoBarras;
    private String nombre;
    private String idCategoria;
    private String nombreCategoria;
    private int stock;
    private double precioCosto;
    private double precioVenta;

    public Producto() {
    }

    public Producto(String idProducto, String codigoBarras, String nombre, String idCategoria,
            String nombreCategoria, int stock, double precioCosto, double precioVenta) {
        this.idProducto = idProducto;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.stock = stock;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
    }

    /**
     * Constructor usado al registrar un producto nuevo (todavia sin idProducto).
     */
    public Producto(String codigoBarras, String nombre, String idCategoria, int stock,
            double precioCosto, double precioVenta) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.stock = stock;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

}
