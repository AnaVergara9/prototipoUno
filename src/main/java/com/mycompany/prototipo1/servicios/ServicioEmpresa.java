/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.servicios;

import com.mycompany.prototipo1.model.Empresa;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author anaso
 */
public class ServicioEmpresa {
    
    public static boolean guardarEmpresa (Empresa empresa){
        try {
            RandomAccessFile file = new RandomAccessFile("data//ejemplo.txt", "rw");
            file.seek(file.length());
            file.writeInt(empresa.getNit());
            file.writeUTF(empresa.getNombre());
            file.writeDouble(empresa.getIngresosAnuales());
            file.writeUTF(empresa.getEstado());
            file.close();
            return true;
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public static Empresa buscarEmpresa (int nit){
        Empresa empresaBuscada = null;
        
        return empresaBuscada;
        
    }
    
    public static boolean eliminarEmpresa (int nit){
        
        return false;
        
    }
    
    public static void actualizarEmpresa (int nit, String nombre, double ingresos, String facturacion){
        
    }
}
