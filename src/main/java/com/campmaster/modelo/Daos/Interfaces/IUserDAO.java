package com.campmaster.modelo.Daos.Interfaces;

import com.campmaster.modelo.Entities.User;

public interface IUserDAO {
    /**
     * This method creates an object in the database
     * @param usr
     * @return
     */
    public boolean create(User usr);

    /**
     * This method reads an object from the database
     *
     * @param ID
     * @return
     */
    public User read(String ID);

    /**
     * This method updates an object in the database
     * @param usr
     * @return
     */
    public boolean update(User usr);

    /**
     * This method deletes an object from the database
     * @param usr
     * @return
     */
    public boolean delete(User usr);
}
