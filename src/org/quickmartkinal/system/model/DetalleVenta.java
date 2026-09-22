/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.quickmartkinal.system.model;

import java.math.BigDecimal;

/**
 *
 * @author informatica
 */
public class DetalleVenta {
    
    private String detailID;
    private String productID;
    private String productName;
    private int amount;
    private BigDecimal unitPrice;
    
    public DetalleVenta(){
        
    }

    public DetalleVenta(String detailID, String productID, String productName, int amount, BigDecimal unitPrice) {
        this.detailID = detailID;
        this.productID = productID;
        this.productName = productName;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }
    
    public BigDecimal getSubtotal(){
        if(unitPrice == null){
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }
    
    @Override
    public String toString() {
        return "DetalleVenta{" +
                "idDetalle='" + detailID + '\'' +
                ", nombreProducto='" + productName + '\'' +
                ", cantidad=" + amount +
                ", precioUnitario=" + unitPrice +
                ", subtotal=" + getSubtotal() +
                '}';
    }
    
    //Getters y Setters
    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
}
