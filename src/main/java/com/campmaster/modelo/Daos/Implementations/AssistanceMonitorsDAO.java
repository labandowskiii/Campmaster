package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.IAssistanceMonitorsDAO;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.Entities.AssistanceMonitors;
import com.campmaster.modelo.extra.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AssistanceMonitorsDAO implements IAssistanceMonitorsDAO {

    private final MainDB mainDB;
    Connection c;

    public AssistanceMonitorsDAO() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }


    public boolean create(String idMonitor, int idActividad) {
        PreparedStatement stmt=null;
        try{
            stmt = c.prepareStatement("INSERT INTO asistencias_monitores (monit_id, activity_id) VALUES (?, ?)");
            stmt.setString(1, idMonitor);
            stmt.setInt(2, idActividad);
            stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void read(int idAssistance) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM asistencias_monitores WHERE id = ?");
            stmt.setInt(1, idAssistance);
            stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int idAssistance) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("DELETE FROM asistencias_monitores WHERE id = ?");
            stmt.setInt(1, idAssistance);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(int idActividad){
        PreparedStatement stmt=null;
        try{
            stmt = c.prepareStatement("DELETE FROM asistencias_monitores WHERE activity_id = ?");
            stmt.setInt(1, idActividad);
            stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<AssistanceMonitors> getMonitorFromActivity(int idActividad){
        PreparedStatement stmt=null;
        try{
            stmt = c.prepareStatement("SELECT * FROM asistencias_monitores WHERE activity_id = ?");
            stmt.setInt(1, idActividad);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            List<AssistanceMonitors> monitors = new ArrayList<AssistanceMonitors>();
            while (rs.next()){
                AssistanceMonitors monitor = EntityMapper.mapRsToClass(rs, AssistanceMonitors.class);
                monitors.add(monitor);
            }
            return monitors;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
