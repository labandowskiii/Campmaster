package com.campmaster.modelo.Daos.Interfaces;

import com.campmaster.modelo.Entities.Booking;

public interface IBookingDAO {
    /**
     * Creates a new registration in the database
     * @param booking
     * @return
     */
    public boolean create(Booking booking);

    /**
     * Reads a registration from the database
     * @param ID
     * @return
     */
    public Booking read(int ID);

    /**
     * Deletes a registration from the database
     * @param booking
     * @return
     */
    public boolean delete(Booking booking);
}
