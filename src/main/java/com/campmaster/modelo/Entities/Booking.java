package com.campmaster.modelo.Entities;

public class Booking {
    int idReserva;
    String idUser;
    int idEvento;

    public Booking(){
        idReserva = 0;
        idUser = "";
        idEvento = 0;
    }

    public Booking(String idUser, int idEvento){
        this.idReserva = 0;
        this.idUser = idUser;
        this.idEvento = idEvento;
    }

    public int getId() {
        return idReserva;
    }
    public void setId(int idReserva) {
        this.idReserva = idReserva;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public int getIdEvento() {
        return idEvento;
    }
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
}


