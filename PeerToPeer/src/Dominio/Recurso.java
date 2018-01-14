/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class Recurso implements Serializable{
   public String nombreRecurso;
   public Integer hashRecurso;
   public String ipRecurso;
   public Integer hashIpRecurso;
   public String rutaRecurso;
   public Boolean recursoPropio;
   private static final long serialVersionUID = 1L;
   
    public Recurso(){}
   
    public Recurso(String nombreRecurso, String ipRecurso, String rutaRecurso,Boolean recursoPropio) {
        this.nombreRecurso = nombreRecurso;
        this.hashRecurso = toHash(nombreRecurso);
        this.ipRecurso = ipRecurso;
        this.hashIpRecurso = toHash(ipRecurso);
        this.rutaRecurso = rutaRecurso;
        this.recursoPropio = recursoPropio;
    }

    public Boolean getRecursoPropio() {
        return recursoPropio;
    }

    public void setRecursoPropio(Boolean recursoPropio) {
        this.recursoPropio = recursoPropio;
    }

    /**
     * Obtiene el nombre del recurso
     * @return el nomre del recurso
     */
    public String getNombreRecurso() {
        return nombreRecurso;
    }

    /**
     * Obtiene el hash del nombre del recurso
     * @return el hash del recurso
     */
    public Integer getHashRecurso() {
        return hashRecurso;
    }

    /**
     * Obtiene el ip del propietario del recurso
     * @return ip del propietario
     */
    public String getIpRecurso() {
        return ipRecurso;
    }

    /**
     * Obtiene el hash del ip del recurso
     * @return hash del ip
     */
    public Integer getHashIpRecurso() {
        return hashIpRecurso;
    }

    /**
     * Obtiene la ruta del recurso
     * @return retorna la ruta del recurso
     */
    public String getRutaRecurso() {
        return rutaRecurso;
    }

    /**
     * Establece el nombre del recurso
     * @param nombreRecurso el nombre
     */
    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    /**
     * Establece el hash del nombre del recurso
     * @param hashRecurso 
     */
    public void setHashRecurso(Integer hashRecurso) {
        this.hashRecurso = hashRecurso;
    }

    /**
     * Establece el ip del propietario del recurso
     * @param ipRecurso 
     */
    public void setIpRecurso(String ipRecurso) {
        this.ipRecurso = ipRecurso;
    }

    /**
     * Establece el hash del ip del recurso
     * @param hashIpRecurso
     */
    public void setHashIpRecurso(Integer hashIpRecurso) {
        this.hashIpRecurso = hashIpRecurso;
    }

    /**
     * Establece la ruta del recurso
     * @param rutaRecurso 
     */
    public void setRutaRecurso(String rutaRecurso) {
        this.rutaRecurso = rutaRecurso;
    }
   
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
 static Integer toHash(String str){
   int strHashCode = Math.abs(str.hashCode() % 100);
   return strHashCode;
}
 
 
 /**
     * metodo que se encarga de crear el archivo el cual almacerana el status 
     * de la descarga actual
     */
    public void crearArchvio(){
        System.out.println("Recurso.crearArchvio: entrando al metodo");
        try {
            File f=null;
            String sfichero="";
                sfichero ="RecursosConocidos//"+this.nombreRecurso+".txt";
                f= new File(sfichero);
                if(!f.exists()){
                    System.out.println("el fichero no existe");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));

                    bw.write(this.nombreRecurso+";"+this.hashRecurso+";"+this.ipRecurso+";"
                            +this.hashIpRecurso+";"+this.rutaRecurso+";"+this.recursoPropio);
                    bw.close();
                    
                }
        } catch (IOException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Recurso.crearArchvio: el metodo termina");
    }
    
    
     public static void eliminarArchivos(){
        File f=null;
        String sfichero= "RecursosConocidos";
        
        f= new File(sfichero);
        if(f.listFiles() != null){
            for (File archivo : f.listFiles()){
                archivo.delete();
            }
        }
    }
     
     
    public static ArrayList<Recurso> ObtenerTodosLosRecursosConocidos(){
        ArrayList<Recurso> recursosConocidos = new ArrayList<Recurso>();
        File f=null;
        try {
            String sfichero= "RecursosConocidos";
            f= new File(sfichero);
            String texto = "";
            BufferedReader br;
            if(f.listFiles() != null){
                for (File archivo : f.listFiles()){
                    br= new BufferedReader(new FileReader(sfichero+"//"+archivo.getName()));
                    texto= br.readLine();
                    recursosConocidos.add(new Recurso(texto.split(";")[0],texto.split(";")[0],texto.split(";")[0],Boolean.parseBoolean(texto.split(";")[0])));
                    br.close();
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Recurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recursosConocidos;
    }
     
    
   
}
