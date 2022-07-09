package com.upn.final_project.entidad;

public class Producto {

    private int id_producto;
    private String tipo_producto;
    private String producto;
    private String ruta_imagen = "";
    private double precio;
    private String comentario="";

    public Producto() {
    }

    public Producto(int id_producto, String tipo_producto, String producto, double precio) {
        this.id_producto = id_producto;
        this.tipo_producto = tipo_producto;
        this.producto = producto;
        this.precio = precio;
    }

    public Producto(String tipo_producto, String producto, String ruta_imagen, double precio, String comentario) {
        this.tipo_producto = tipo_producto;
        this.producto = producto;
        this.ruta_imagen = ruta_imagen;
        this.precio = precio;
        this.comentario = comentario;
    }

    public Producto(int id_producto, String tipo_producto, String producto, String ruta_imagen, double precio, String comentario) {
        this.id_producto = id_producto;
        this.tipo_producto = tipo_producto;
        this.producto = producto;
        this.ruta_imagen = ruta_imagen;
        this.precio = precio;
        this.comentario = comentario;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
