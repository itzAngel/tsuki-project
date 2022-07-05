package com.upn.final_project.entidad;

public class Pedido {

    private int id_pedido;
    private int id_usuario;
    private double sub_total;
    private double envio;
    private double total;

    public Pedido() {
    }

    public Pedido(int id_pedido, int id_usuario, double sub_total, double envio, double total) {
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.sub_total = sub_total;
        this.envio = envio;
        this.total = total;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getEnvio() {
        return envio;
    }

    public void setEnvio(double envio) {
        this.envio = envio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
