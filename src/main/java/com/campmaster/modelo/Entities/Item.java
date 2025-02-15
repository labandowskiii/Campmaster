package com.campmaster.modelo.Entities;

public class Item {


    int id;
    String nombre;
    String marca;
    int cantidad;

    public Item(){
        id =0;
        nombre=null;
        marca=null;
        cantidad=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre + " Marca: " + marca + " Cantidad: " + cantidad;
    }
}
