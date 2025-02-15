package com.campmaster.modelo.Daos.Interfaces;

public interface ISettingsDAO {
    /**
     * Reads a setting from the database
     * @param key
     * @return
     */
    public String read(String key);
}
