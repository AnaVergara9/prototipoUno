/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.model;

/**
 *
 * @author pauda
 */
public class EmpleadoLista {
    int idEmpleado;
    int nitEmpresa;
    String nombre;
    double salario;
    String estado;
    String nombreEmpresa;
    
    public EmpleadoLista(int idEmpleado, int nitEmpresa, String nombre, double salario, String estado, String nombreEmpresa) {
        this.idEmpleado = idEmpleado;
        this.nitEmpresa = nitEmpresa;
        this.nombre = nombre;
        this.salario = salario;
        this.estado = estado;
        this.nombreEmpresa = nombreEmpresa;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(int nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
}
