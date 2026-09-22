/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author informatica
 */
public class Venta {
    //Atributos
    private String salesID;
    private Usuario user;
    private LocalDateTime date;
    private List<DetalleVenta>detalles;
    
    public Venta () {
        this.detalles = new ArrayList<>();
    }

    public Venta(String salesID, Usuario user, LocalDateTime date, List<DetalleVenta> detalles) {
        this.salesID = salesID;
        this.user = user;
        this.date = date;
        this.detalles = detalles;
    }
    
    public void agregarDetalle(DetalleVenta detalle){
        this.detalles.add(detalle);
    }
    
    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles){
            total = total.add(detalle.getSubtotal());
        }
        return total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta='" + salesID + '\'' +
                ", fecha=" + date +
                ", usuario=" + (user != null ? user.getNombreUsuario() : "null") +
                ", cantidadProductos=" + detalles.size() +
                ", total=" + getTotal() +
                '}';
    }
    
    
    //Getters y Setters
    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    
    
}
