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
public class Finger {
    public String ip;
    public String hash_ip;

    public Finger(){}
    
    public Finger(String ip, String hash_ip) {
        this.ip = ip;
        this.hash_ip = hash_ip;
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
    public String getHash_ip() {
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
    public void setHash_ip(String hash_ip) {
        this.hash_ip = hash_ip;
    }
    
    
}
