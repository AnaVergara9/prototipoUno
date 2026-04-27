/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.servicios;

import com.mycompany.prototipo1.data.DataBaseConnection;
import com.mycompany.prototipo1.model.Empresa;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author anaso
 */
public class ServicioEmpresa {
    
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
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
        //Valida que no exista la empresa
        if (buscarEmpresa(empresa.getNit())!=null){
            return false;
        }
        
        //Valida que otra empresa no se llame igual
        if (buscarEmpresaPorNombre(empresa.getNombre())!=null){
            return false;
        }
        
        try {
            conn = DataBaseConnection.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();
                //String insertDataSQL = "INSERT INTO EMPLEADO (CODIGO, NOMBRE) VALUES (" + dpto.getCodigo() + ",'" + dpto.getNombre() + "')";
                String insertDataSQL = "INSERT INTO EMPRESA (NIT, NOMBRE, INGRESOS, FACTURACION, ESTADO) VALUES(" + empresa.getNit() + ", '" +  empresa.getNombre() + "', '" + empresa.isFacturacion() + "', '" + empresa.getEstado() + "');";
                int rowsAffected = stmt.executeUpdate(insertDataSQL);
                conn.close();
            } else {
                return false;
            }
            /* 
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            file.seek(file.length());
            file.writeInt(empresa.getNit());
            file.writeUTF(ajustarTamaño(TAM_NOMBRE, empresa.getNombre()));
            file.writeDouble(empresa.getIngresosAnuales());
            file.writeBoolean(empresa.isFacturacion());
            file.writeUTF(ajustarTamaño(TAM_ESTADO,empresa.getEstado()));
            file.close();
            return true;
            */   
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
            return false;
        }
        return true;
    }
    
    public static Empresa buscarEmpresa (int nitBuscado){
        try {
            conn = DataBaseConnection.getConnection();
            
            if (conn != null) {
               stmt = conn.createStatement();
                String selectDataSQL = "SELECT * FROM EMPRESA;"; // Completar
                rs = stmt.executeQuery(selectDataSQL);
                
                while (rs.next()) {
                    int nit = rs.getInt("NIT");
                    String nombre = rs.getString("NOMBRE");
                    double ingresos = rs.getDouble("INGRESOS");
                    boolean facturacion = rs.getBoolean("FACTURACION");
                    String estado = rs.getString("ESTADO");
                    
                    if (nit == nitBuscado &&  estado.equals("AC")){
                    Empresa empresaBuscada = new Empresa (nit, nombre,ingresos,facturacion,estado);
                    return empresaBuscada;
                    }
                }
                conn.close(); 
            }
           /* RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double ingresos = file.readDouble();
                boolean facturacion = file.readBoolean();
                String estado = file.readUTF().trim();
                if (nit == nitBuscado &  estado.equals("Activo")){
                    Empresa empresaBuscada = new Empresa (nit, nombre,ingresos,facturacion,estado);
                    return empresaBuscada;
                }
            } */     
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        }
        return null;
    }
        
    
    public static boolean eliminarEmpresa (int nit){
        //int pos = (contarRegistros(nit)*TAM_REGISTRO);
        Empresa empresaBuscada = ServicioEmpresa.buscarEmpresa(nit);
        if (empresaBuscada == null){
            return false;
        }
        
        try {
            conn = DataBaseConnection.getConnection();
            if (conn != null) {
               stmt = conn.createStatement();
                String insertDataSQL = "UPDATE EMPRESA SET ESTADO = 'IN' WHERE NIT = " + nit + ";";
                int rowsAffected = stmt.executeUpdate(insertDataSQL);
                conn.close();
            } else {
                return false;
            }
           /* RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            file.seek(pos-10);
            file.writeUTF(ajustarTamaño(TAM_ESTADO,"Inactivo"));
            file.close();S
            */
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
            return false;
        }
        return true;
        
    }
    
    
    public static boolean actualizarEmpresa (Empresa empresa){
        Empresa empresaBuscada = ServicioEmpresa.buscarEmpresa(empresa.getNit());
        
        //Valida que exista la empresa y este activa
        if (empresaBuscada == null || !empresa.getEstado().equals("Activo")){
            return false;
        }
        try {
            /*RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF();
                double ingresos = file.readDouble();
                boolean facturacion = file.readBoolean();
                String estado = file.readUTF();
            */
            conn = DataBaseConnection.getConnection();
            if (conn != null) {
               stmt = conn.createStatement();
                String insertDataSQL = "UPDATE EMPRESA SET NOMBRE = '" + empresa.getNombre() + "', '" + empresa.isFacturacion() + "' WHERE NIT = " + empresa.getNit() + ";";
                int rowsAffected = stmt.executeUpdate(insertDataSQL);
                conn.close();
            } else {
                return false;
            }     
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
            return false;
        }
        return true;
    }
    
    
    public static int contarRegistros(int pNit){
        int contador = 0;
        
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF();
                double ingresos = file.readDouble();
                boolean facElec = file.readBoolean();
                String estado = file.readUTF();
                contador ++;
                
                if (nit == pNit){
                    file.close();
                    return contador;
                }
            }
            file.close();  
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        }
        return -1;
    }
    
    public static List obtenerEmpresas(){
        //Se declara una variable llamada empresas de tipo ArrayList que permite "almacenar" objetos de tipo empresa
        List <Empresa> empresas = new ArrayList();
        int nit;
        String nombre;
        double ingresos;
        boolean facElec;
        String estado = "activo";
        Empresa emp = null;
        
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            
            file.seek(0);
            
            while(file.getFilePointer() < file.length()){
                
                nit = file.readInt();
                nombre = file.readUTF().trim();
                ingresos = file.readDouble();
                facElec = file.readBoolean();
                estado = file.readUTF().trim();
                
                emp = new Empresa(nit, nombre, ingresos, facElec, estado);
                empresas.add(emp);
            }
            file.close();
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        }
        return empresas;
    }
    
    public static double sumatoria(){
        double sumatoria = 0;
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double ingresos = file.readDouble();
                boolean facElec = file.readBoolean();
                String estado = file.readUTF().trim();
                
                if (estado.equalsIgnoreCase("Activo")){
                    sumatoria = sumatoria + ingresos;
                }
            }
            file.close();
            return sumatoria;
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        }
        return 0;
    }
    
    public static int registrosActivos(){
        int contador = 0;
        
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double ingresos = file.readDouble();
                boolean facElec = file.readBoolean();
                String estado = file.readUTF().trim();
                
                if (estado.equalsIgnoreCase("Activo")){
                    contador ++;
                }
            }
            file.close();
            return contador;
            
        } catch (Exception ex) {
            System.out.println("Error! " + ex);
        }
        return 0;
    }

    private static Empresa buscarEmpresaPorNombre(String nombreBuscado) {
        try {
            RandomAccessFile file = new RandomAccessFile("data//empresas.txt", "rw");
            
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double ingresos = file.readDouble();
                boolean facturacion = file.readBoolean();
                String estado = file.readUTF().trim();
                if (nombre == nombreBuscado &  estado.equals("Activo")){
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
        
}
