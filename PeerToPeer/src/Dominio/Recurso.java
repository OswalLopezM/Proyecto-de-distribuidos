/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.io.Serializable;

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
   
}
