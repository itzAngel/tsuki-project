package com.upn.final_project.entidad;

public class Mascota {

    private int id;
    private String tipo;
    private String nombreMascota;
    private double peso;
    private int edad;
    private String nombreDueno;

    public Mascota() {
    }

    public Mascota(String tipo, String nombreMascota, double peso, int edad, String nombreDueno) {
        this.tipo = tipo;
        this.nombreMascota = nombreMascota;
        this.peso = peso;
        this.edad = edad;
        this.nombreDueno = nombreDueno;
    }

    public Mascota(int id, String tipo, String nombreMascota, double peso, int edad, String nombreDueno) {
        this.id = id;
        this.tipo = tipo;
        this.nombreMascota = nombreMascota;
        this.peso = peso;
        this.edad = edad;
        this.nombreDueno = nombreDueno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }
}
