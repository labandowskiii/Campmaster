package com.campmaster.modelo.Entities;
import com.campmaster.modelo.extra.Validation;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Event implements Validation {
    int id;
    String nombre;
    Date fecha_inicio;
    Date fecha_fin;
    String lugar;
    String descripcion;
    int max_participantes;

    public Event() {
    }

    public Event(int id, String nombre, Date fecha_inicio, Date fecha_fin, String lugar, String descripcion, int max_participantes) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.max_participantes = max_participantes;
    }

    public Event(String nombre, Date fecha_inicio, Date fecha_fin, String lugar, String descripcion, int max_participantes) {
        this.id = 0;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.max_participantes = max_participantes;
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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMax_participantes() {
        return max_participantes;
    }

    public void setMax_participantes(int max_participantes) {
        this.max_participantes = max_participantes;
    }


    public String normalizaFecha(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
        return sdf.format(fecha);
    }

    @Override
    public boolean validate() {
        return nombre!=null && fecha_inicio !=null && fecha_fin !=null && lugar!=null && descripcion!=null && max_participantes>0 && (fecha_fin.after(fecha_inicio)|| fecha_fin.equals(fecha_inicio));
    }

    /**
     * EJEMPLO DE COMO CONVERTIR UNA FECHA DE JAVA A SQL
     * java.util.Date javaDate = new java.util.Date();
     * java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
     */
}
