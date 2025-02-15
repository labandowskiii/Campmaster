package com.campmaster.modelo.Daos.Implementations;
import com.campmaster.modelo.Daos.Interfaces.IEventDAO;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.Entities.Event;
import com.campmaster.modelo.extra.EntityMapper;
import com.campmaster.modelo.extra.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements IEventDAO {

    private final MainDB mainDB;
    Connection c;

    public EventDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }

    @Override
    public boolean create(Event event) {
        try {
            event.validate();
        } catch (Exception e) {
            return false;
        }
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("INSERT INTO eventos (nombre, fecha_inicio, fecha_fin, lugar, descripcion, max_participantes) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, event.getNombre());
            stmt.setDate(2, event.getFecha_inicio());
            stmt.setDate(3, event.getFecha_fin());
            stmt.setString(4, event.getLugar());
            stmt.setString(5, event.getDescripcion());
            stmt.setInt(6, event.getMax_participantes());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Event read(int ID) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos WHERE id = ?");
            stmt.setInt(1, ID);
            stmt.executeQuery();
            return EntityMapper.mapRsToClass(stmt.getResultSet(), Event.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Event event) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("UPDATE eventos SET nombre = ?, fecha_inicio = ?, fecha_fin = ?, lugar = ?, descripcion = ?, max_participantes = ? WHERE id = ?");
            stmt.setString(1, event.getNombre());
            stmt.setDate(2, event.getFecha_inicio());
            stmt.setDate(3, event.getFecha_fin());
            stmt.setString(4, event.getLugar());
            stmt.setString(5, event.getDescripcion());
            stmt.setInt(6, event.getMax_participantes());
            stmt.setInt(7, event.getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Event event) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("DELETE FROM eventos WHERE id = ?");
            stmt.setInt(1, event.getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Event> listEventsRange(Date date1) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos WHERE fecha_inicio >= ?");
            stmt.setDate(1, date1);
            ResultSet rs = stmt.executeQuery();
            List<Event> events = new ArrayList<Event>();
            while (rs.next()) {
                Event event = EntityMapper.mapRsToClass(rs, Event.class);
                events.add(event);
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Event> listAllEvents() {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos");
            ResultSet rs = stmt.executeQuery();
            List<Event> events = new ArrayList<Event>();
            while (rs.next()) {
                Event event = EntityMapper.mapRsToClass(rs, Event.class);
                events.add(event);
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Event getLast() {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos ORDER BY id DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return EntityMapper.mapRsToClass(rs, Event.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getEventBookings(int id) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT COUNT(*) FROM reservas WHERE id_actividad = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Event> listParticipanteEvents(String dni) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos WHERE id IN (SELECT id_actividad FROM reservas WHERE id_participante = ?)");
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            List<Event> events = new ArrayList<Event>();
            while (rs.next()) {
                Event event = EntityMapper.mapRsToClass(rs, Event.class);
                events.add(event);
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Event> listMonitorEvents(String dni) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos WHERE id IN (SELECT activity_id FROM asistencias_monitores WHERE monit_id = ?)");
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            List<Event> events = new ArrayList<Event>();
            while (rs.next()) {
                Event event = EntityMapper.mapRsToClass(rs, Event.class);
                events.add(event);
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUserRegistered() {
        String id = SessionManager.getInstance().getUser().getDni();
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM eventos WHERE id IN (SELECT id_actividad FROM  reservas WHERE id_participante=?)");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
