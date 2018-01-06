/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author oswal
 */
public class Recurso {
   public String nombreRecurso;
   public String hashRecurso;
   public String ipRecurso;
   public String hashIpRecurso;
   public String rutaRecurso;
   
    public Recurso(){}
   
    public Recurso(String nombreRecurso, String hashRecurso, String ipRecurso, String hashIpRecurso, String rutaRecurso) {
        this.nombreRecurso = nombreRecurso;
        this.hashRecurso = hashRecurso;
        this.ipRecurso = ipRecurso;
        this.hashIpRecurso = hashIpRecurso;
        this.rutaRecurso = rutaRecurso;
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
    public String getHashRecurso() {
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
    public String getHashIpRecurso() {
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
    public void setHashRecurso(String hashRecurso) {
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
    public void setHashIpRecurso(String hashIpRecurso) {
        this.hashIpRecurso = hashIpRecurso;
    }

    /**
     * Establece la ruta del recurso
     * @param rutaRecurso 
     */
    public void setRutaRecurso(String rutaRecurso) {
        this.rutaRecurso = rutaRecurso;
    }
   
   
}
