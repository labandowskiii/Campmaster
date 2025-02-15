package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.IBookingDAO;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.Entities.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingDAOImpl implements IBookingDAO {

    private final MainDB mainDB;
    Connection c;

    public BookingDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }

    @Override
    public boolean create(Booking booking) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("INSERT INTO reservas (id_actividad, id_participante) VALUES (?, ?)");
            stmt.setInt(1, booking.getIdEvento());
            stmt.setString(2, booking.getIdUser());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Booking read(int ID) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM reservas WHERE id_reserva = ?");
            stmt.setInt(1, ID);
            stmt.executeQuery();
            return new Booking();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Booking booking) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("DELETE FROM reservas WHERE id_reserva = ?");
            stmt.setInt(1, booking.getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllActivityBookings(int idActivity) {
        PreparedStatement stmt=null;
        try{
            stmt=c.prepareStatement("DELETE FROM reservas WHERE id_actividad=?");
            stmt.setInt(1,idActivity);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
