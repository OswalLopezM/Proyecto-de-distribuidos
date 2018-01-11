/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author mariangel
 */
public class Finger {
    public Integer posicion;
    public String ip;
    public Integer hash_ip;
    public Integer puertoTexto;
    public Integer puertoArchivo;

    public Finger(){}
    
    public Finger(Integer posicion, String ip, Integer hash_ip, Integer puertoTexto, Integer puertoArchivo) {
        this.posicion = posicion;
        this.ip = ip;
        this.hash_ip = hash_ip;
        this.puertoTexto = puertoTexto;
        this.puertoArchivo = puertoArchivo;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public String getIp() {
        return ip;
    }

    public Integer getHash_ip() {
        return hash_ip;
    }

    public Integer getPuertoTexto() {
        return puertoTexto;
    }

    public Integer getPuertoArchivo() {
        return puertoArchivo;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setHash_ip(Integer hash_ip) {
        this.hash_ip = hash_ip;
    }

    public void setPuertoTexto(Integer puertoTexto) {
        this.puertoTexto = puertoTexto;
    }

    public void setPuertoArchivo(Integer puertoArchivo) {
        this.puertoArchivo = puertoArchivo;
    }
    
    
    
}
