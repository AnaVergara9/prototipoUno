/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.servicios;

import com.mycompany.prototipo1.model.Empresa;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

/**
 *
 * @author anaso
 */
public class ServicioEmpresa {
    
    public static final int TAM_NOMBRE = 25;
    public static final int TAM_ESTADO = 8;
    public static final int TAM_REGISTRO = 50;
    
    public static String ajustarTamaño(int m, String s) {
    if (s.length() >= m) {
        return s.substring(0, m);
    } else {
        return String.format("%-" + m + "s", s);
    }
   }
    
    public static boolean guardarEmpresa (Empresa empresa){
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            file.seek(file.length());
            file.writeInt(empresa.getNit());
            file.writeUTF(ajustarTamaño(TAM_NOMBRE, empresa.getNombre()));
            file.writeDouble(empresa.getIngresosAnuales());
            file.writeBoolean(empresa.isFacturacion());
            file.writeUTF(ajustarTamaño(TAM_ESTADO,empresa.getEstado()));
            file.close();
            return true;
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public static Empresa buscarEmpresa (int nitBuscado){
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF();
                double ingresos = file.readDouble();
                boolean facturacion = file.readBoolean();
                String estado = file.readUTF();
                if (nit == nitBuscado){
                    Empresa empresaBuscada = new Empresa (nit, nombre,ingresos,facturacion,estado);
                    return empresaBuscada;
                }
            }
            file.close();
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
        return null;
    }
    
    public static boolean eliminarEmpresa (int nit){
        
        return false;
        
    }
    
    public static boolean actualizarEmpresa (Empresa empresa){
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF();
                double ingresos = file.readDouble();
                boolean facturacion = file.readBoolean();
                String estado = file.readUTF();
          
                if (nit == empresa.getNit()){
                    file.seek(file.getFilePointer() - TAM_REGISTRO);
                    file.writeInt(empresa.getNit());
                    file.writeUTF(ajustarTamaño(TAM_NOMBRE, empresa.getNombre()));
                    file.writeDouble(empresa.getIngresosAnuales());
                    file.writeBoolean(empresa.isFacturacion());
                    file.writeUTF(ajustarTamaño(TAM_ESTADO,empresa.getEstado()));
                    file.close();    
                    return true;
                }
            }
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
        return false;
    }
}
