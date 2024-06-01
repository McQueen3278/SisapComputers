package org.harolrodriguez.bean;


public class Computadoras {
    private int computadoraID;
    private String modelo;
    private String marca;
    private String descripcion;
    private String departamentoAsignado;

    public Computadoras() {
        
    }

    public Computadoras(int computadoraID, String modelo, String marca, String descripcion, String departamentoAsignado) {
        this.computadoraID = computadoraID;
        this.modelo = modelo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.departamentoAsignado = departamentoAsignado;
    }

    public int getComputadoraID() {
        return computadoraID;
    }

    public void setComputadoraID(int computadoraID) {
        this.computadoraID = computadoraID;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDepartamentoAsignado() {
        return departamentoAsignado;
    }

    public void setDepartamentoAsignado(String departamentoAsignado) {
        this.departamentoAsignado = departamentoAsignado;
    }
    
    
}
