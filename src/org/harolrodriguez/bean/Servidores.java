package org.harolrodriguez.bean;

public class Servidores {
    private int servidorID;
    private String tipoServidor;
    private String descripcion;
    private String marca;

    public Servidores() {
        
    }

    public Servidores(int servidorID, String tipoServidor, String descripcion, String marca) {
        this.servidorID = servidorID;
        this.tipoServidor = tipoServidor;
        this.descripcion = descripcion;
        this.marca = marca;
    }

    public int getServidorID() {
        return servidorID;
    }

    public void setServidorID(int servidorID) {
        this.servidorID = servidorID;
    }

    public String getTipoServidor() {
        return tipoServidor;
    }

    public void setTipoServidor(String tipoServidor) {
        this.tipoServidor = tipoServidor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
}
