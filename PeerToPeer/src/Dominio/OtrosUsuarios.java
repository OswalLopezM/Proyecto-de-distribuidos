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
public class OtrosUsuarios {
    public String ip;
    public Integer hash_ip;
    public Integer puertoArchivo;
    public Integer puertoTexto;

    public OtrosUsuarios(){}

    public OtrosUsuarios(String ip, Integer hash_ip, Integer puertoArchivo, Integer puertoTexto) {
        this.ip = ip;
        this.hash_ip = hash_ip;
        this.puertoArchivo = puertoArchivo;
        this.puertoTexto = puertoTexto;
    }

    /**
     * Obtiene el ip
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Obtiene el hash del ip
     * @return el hash de la ip
     */
    public Integer getHash_ip() {
        return hash_ip;
    }

    /**
     * Establece la ip
     * @param ip la ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Establece el hash del ip
     * @param hash_ip el hash del ip
     */
    public void setHash_ip(Integer hash_ip) {
        this.hash_ip = hash_ip;
    }

    public Integer getPuertoArchivo() {
        return puertoArchivo;
    }

    public void setPuertoArchivo(Integer puertoArchivo) {
        this.puertoArchivo = puertoArchivo;
    }

    public Integer getPuertoTexto() {
        return puertoTexto;
    }

    public void setPuertoTexto(Integer puertoTexto) {
        this.puertoTexto = puertoTexto;
    }
    
    
    
    
}
