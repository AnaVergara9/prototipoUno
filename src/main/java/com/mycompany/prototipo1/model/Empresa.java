/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.model;

/**
 *
 * @author anaso
 */
public class Empresa {
    int nit;
    String nombre;
    double ingresosAnuales;
    boolean facturacion;
    String estado;

    public Empresa(int nit, String nombre, double ingresosAnuales, boolean facturacion, String estado) {
        this.nit = nit;
        this.nombre = nombre;
        this.ingresosAnuales = ingresosAnuales;
        this.facturacion = facturacion;
        this.estado = estado;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getIngresosAnuales() {
        return ingresosAnuales;
    }

    public void setIngresosAnuales(double ingresosAnuales) {
        this.ingresosAnuales = ingresosAnuales;
    }

    public boolean isFacturacion() {
        return facturacion;
    }

    public void setFacturacion(boolean facturacion) {
        this.facturacion = facturacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
