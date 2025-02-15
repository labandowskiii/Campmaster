package com.campmaster.modelo.Daos.Interfaces;

import com.campmaster.modelo.Entities.Event;

import java.sql.Date;
import java.util.List;

public interface IEventDAO {
    /**
     * This method creates an object in the database
     * @param event
     * @return
     */
    public boolean create(Event event);

    /**
     * This method reads an object from the database
     *
     * @param ID
     * @return
     */
    public Event read(int ID);

    /**
     * This method updates an object in the database
     * @param event
     * @return
     */
    public boolean update(Event event);

    /**
     * This method deletes an object from the database
     * @param event
     * @return
     */
    public boolean delete(Event event);

    /**
     * This method lists all the events included
     * between a range of dates
     */
    public List<Event> listEventsRange(Date date1);
}
