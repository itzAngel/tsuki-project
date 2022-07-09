package com.upn.final_project.entidad;

public class Carrito {

    private int id_carrito;
    private int id_pedido;
    private int id_producto;
    private int cantidad;
    private double total_carrito;

    public Carrito() {
    }

    public Carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public Carrito(int id_pedido, int id_producto, int cantidad, double total_carrito) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.total_carrito = total_carrito;
    }

    public Carrito(int id_carrito, int id_pedido, int id_producto, int cantidad, double total_carrito) {
        this.id_carrito = id_carrito;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.total_carrito = total_carrito;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal_carrito() {
        return total_carrito;
    }

    public void setTotal_carrito(double total_carrito) {
        this.total_carrito = total_carrito;
    }
}
