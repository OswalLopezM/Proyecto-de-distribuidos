package Dominio;

/**
 *
 * @author oswal
 */
public class Usuario {
    protected String ip;
    protected String hashIp;

    public Usuario(String ip) {
        this.ip = ip;
        this.hashIp = toHash(ip).toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHashIp() {
        return hashIp;
    }

    public void setHashIp(String hashIp) {
        this.hashIp = hashIp;
    }
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
    private Integer toHash(String ip){
        Integer hash = 512;
        hash =  37*hash + ip.hashCode();
        return hash;
    }
}
