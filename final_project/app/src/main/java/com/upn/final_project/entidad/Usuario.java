package com.upn.final_project.entidad;

public class Usuario {

    private int id_usuario;
    private String nombre;
    private int celular;
    private String direccion;
    private String email;

    public Usuario() {
    }

    public Usuario(String nombre, int celular, String direccion, String email) {
        this.nombre = nombre;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
    }

    public Usuario(int id, String nombre, int celular, String direccion, String email) {
        this.id_usuario = id;
        this.nombre = nombre;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
    }

    public int getId_usuario() { return id_usuario; }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
