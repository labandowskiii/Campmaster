package com.campmaster.modelo.Entities;

import com.campmaster.modelo.extra.Validation;

public class User implements Validation {
    String dni;
    String Contraseña;
    String Nombre;
    String Apellidos;
    String Correo;
    String tipo;
    String nombreTutor;
    String numeroTutor;


    public User(){

    }

    public User(String dni, String contraseña, String nombre, String apellidos, String correo, String tipo, String nombreTutor, String numeroTutor) {
        this.dni = dni;
        this.Contraseña = contraseña;
        this.Nombre = nombre;
        this.Apellidos = apellidos;
        this.Correo = correo;
        this.tipo = tipo;
        this.nombreTutor = "";
        this.numeroTutor = "";
    }

    public User(User user) {
        this.dni = user.dni;
        this.Contraseña = user.Contraseña;
        this.Nombre = user.Nombre;
        this.Apellidos = user.Apellidos;
        this.Correo = user.Correo;
        this.tipo = user.tipo;
        this.nombreTutor = user.nombreTutor;
        this.numeroTutor = user.numeroTutor;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        this.Contraseña = contraseña;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        this.Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        this.Correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getNumeroTutor() {
        return numeroTutor;
    }

    public void setNumeroTutor(String numeroTutor) {
        this.numeroTutor = numeroTutor;
    }

    @Override
    public boolean validate() {
        return dni.length() == 9 && Character.isLetter(dni.charAt(8)) && !Nombre.isEmpty() && !Apellidos.isEmpty() && !Correo.isEmpty();
    }

    /**
     * Este metodo es asi para mostrarlo en la vista de seleccion de los monitores, aunque aplique a todos los usuarios
     * @return
     */
    @Override
    public String toString() {
        return getNombre() + " " + getApellidos();
    }
}

