package com.campmaster.modelo.Daos.Interfaces;

public interface IAssistanceMonitorsDAO {

    public boolean create(String idMonitor, int idActividad);

    public void read(int idAssistance);

    public void delete(int idAssistance);

}
