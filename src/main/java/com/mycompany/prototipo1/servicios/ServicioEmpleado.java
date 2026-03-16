/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prototipo1.servicios;

import com.mycompany.prototipo1.model.Empleado;
import com.mycompany.prototipo1.model.Empresa;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anaso
 */
public class ServicioEmpleado {
    public static final String NOMBRE_ARCHIVO = "data//empleados.txt";
    public static final int TAM_NOMBRE = 20;
    public static final int TAM_ESTADO = 8;
    public static final int TAM_REGISTRO = 48;
    
    public static String ajustarTamaño(int m, String s) {
    if (s.length() >= m) {
        return s.substring(0, m);
    } else {
        return String.format("%-" + m + "s", s);
    }
   }
    
    public static boolean guardarEmpleado (Empleado empleado){
        
        Empleado empleadobuscado = buscarEmpleado(empleado.getIdEmpleado());
        Empresa empresa = ServicioEmpresa.buscarEmpresa(empleado.getNitEmpresa());
        
        //Valida que no exista el ID
        if (empleadobuscado != null){
            return false;
        }
        
        //Valida que exista la empresa y este activa
        if (empresa == null || !empresa.getEstado().equals("Activo")){
            return false;
        }
        
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            file.seek(file.length());
            file.writeInt(empleado.getIdEmpleado());
            file.writeInt(empleado.getNitEmpresa());
            file.writeUTF(ajustarTamaño(TAM_NOMBRE, empleado.getNombre()));
            file.writeDouble(empleado.getSalario());
            file.writeUTF(ajustarTamaño(TAM_ESTADO,empleado.getEstado()));
            file.close();
            return true;
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
    }
    
    public static Empleado buscarEmpleado (int idBuscado){
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double salario = file.readDouble();
                String estado = file.readUTF().trim();
                if (nit == idBuscado &  estado.equals("Activo")){
                    Empleado empleadoBuscado = new Empleado (id, nit, nombre,salario,estado);
                    return empleadoBuscado;
                }
            }
            file.close();
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
        return null;
    }
    
    public static boolean eliminarEmpleado (int id) throws IOException{
        int pos = (contarRegistros(id)*TAM_REGISTRO);
        
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            
            file.seek(pos-10);
            file.writeUTF(ajustarTamaño(TAM_ESTADO,"Inactivo"));
            
            file.close();

        } catch (FileNotFoundException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
        return true;
        
    }
    
    public static boolean actualizarEmpleado (Empleado empleado){
        Empleado empleadobuscado = buscarEmpleado(empleado.getIdEmpleado());
        Empresa empresa = ServicioEmpresa.buscarEmpresa(empleado.getNitEmpresa());
        
        //Valida que exista el empleado - empleado y empresa activos
        if (empleadobuscado == null || !empleadobuscado.getEstado().equals("Activo") || !empresa.getEstado().equals("Activo")){
            return false;
        }
        
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF();
                double salario = file.readDouble();
                String estado = file.readUTF().trim();
          
                if (nit == empleado.getIdEmpleado()){
                    file.seek(file.getFilePointer() - TAM_REGISTRO);
                    file.writeInt(empleado.getIdEmpleado());
                    file.writeInt(empleado.getNitEmpresa());
                    file.writeUTF(ajustarTamaño(TAM_NOMBRE, empleado.getNombre()));
                    file.writeDouble(empleado.getSalario());
                    file.writeUTF(ajustarTamaño(TAM_ESTADO,empleado.getEstado()));
                    file.close();    
                    return true;
                }
            }
        } catch (IOException ex) {
            System.getLogger(ServicioEmpresa.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
        return false;
    }
    
    public static int contarRegistros(int pNit){
        int contador = 0;
        
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF();
                double salario = file.readDouble();
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
    
    public static List obtenerEmpleado(){
        
        List <Empleado> empresas = new ArrayList();

        Empleado emp = null;
        
        try {
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            file.seek(0);
            
            while(file.getFilePointer() < file.length()){
                int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double salario = file.readDouble();
                String estado = file.readUTF().trim();
                
                emp = new Empleado(id, nit, nombre, salario, estado);
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
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                 int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double salario = file.readDouble();
                String estado = file.readUTF().trim();
                
                if (estado.equalsIgnoreCase("Activo")){
                    sumatoria = sumatoria + salario;
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
            RandomAccessFile file = new RandomAccessFile(NOMBRE_ARCHIVO, "rw");
            //Se posiciona al inicio del archivo
            file.seek(0);
            while(file.getFilePointer() < file.length()){
                 int id = file.readInt();
                int nit = file.readInt();
                String nombre = file.readUTF().trim();
                double salario = file.readDouble();
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
    
}
