package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.IUserDAO;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.Entities.User;
import com.campmaster.modelo.extra.EntityMapper;
import com.campmaster.modelo.extra.SessionManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDAOImpl implements IUserDAO {

    private final MainDB mainDB;
    Connection c;

    public UserDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }



    @Override
    public boolean create(User usr) {
        try {
            usr.validate();
        }catch (Exception e){
            return false;
        }
        String hashedPass = BCrypt.hashpw(usr.getContraseña(), BCrypt.gensalt());
        usr.setContraseña(hashedPass);
        PreparedStatement stmt = null;
        if (usr.getTipo().equals("Participante")){
            try {
                stmt = c.prepareStatement("INSERT INTO usuarios (dni, Nombre, Correo, Contraseña, tipo, NombreTutor, NumeroTutor, Apellidos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                stmt.setString(1, usr.getDni());
                stmt.setString(2, usr.getNombre());
                stmt.setString(3, usr.getCorreo());
                stmt.setString(4, usr.getContraseña());
                stmt.setString(5, usr.getTipo());
                stmt.setString(6, usr.getNombreTutor());
                stmt.setString(7, usr.getNumeroTutor());
                stmt.setString(8, usr.getApellidos());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        else{
            try {
                stmt = c.prepareStatement("INSERT INTO usuarios (dni, Nombre, Correo, Contraseña, Apellidos,  tipo) VALUES (?, ?, ?, ?, ?, ?)");
                stmt.setString(1, usr.getDni());
                stmt.setString(2, usr.getNombre());
                stmt.setString(3, usr.getCorreo());
                stmt.setString(4, usr.getContraseña());
                stmt.setString(5, usr.getApellidos());
                stmt.setString(6, usr.getTipo());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public User read(String ID) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM usuarios WHERE dni=?");
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User usuario= EntityMapper.mapRsToClass(rs, User.class);
                return usuario;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(User usr) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("UPDATE usuarios SET Nombre=?, Apellidos=?, Correo=?, Contraseña=?, tipo=?, NombreTutor=?, NumeroTutor=? WHERE dni=?");
            stmt.setString(1, usr.getNombre());
            stmt.setString(2, usr.getApellidos());
            stmt.setString(3, usr.getCorreo());
            stmt.setString(4, usr.getContraseña());
            stmt.setString(5, usr.getTipo());
            stmt.setString(6, usr.getNombreTutor());
            stmt.setString(7, usr.getNumeroTutor());
            stmt.setString(8, usr.getDni());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User usr) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("DELETE FROM usuarios WHERE dni=?");
            stmt.setString(1, usr.getDni());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String dni, String pw) {
        User log = read(dni);
        if (log == null) {
            return false;
        }
        if (BCrypt.checkpw(pw, log.getContraseña())){
            SessionManager sm = SessionManager.getInstance();
            sm.login(log);
            return true;
            }
        return false;
        }

    public boolean validateDni(String dni){
        try {
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM usuarios WHERE dni=?");
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> readAllMonitors(){
        PreparedStatement stmt = null;
        try{
            stmt = c.prepareStatement("SELECT * FROM usuarios WHERE tipo='Monitor'");
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()){
                User user = EntityMapper.mapRsToClass(rs, User.class);
                users.add(user);
            }
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getEventMonitors(int idEvent){
        PreparedStatement stmt = null;
        try{
            stmt = c.prepareStatement("SELECT * FROM usuarios WHERE dni IN (SELECT monit_id FROM asistencias_monitores WHERE activity_id = ?)");
            stmt.setInt(1, idEvent);
            ResultSet rs = stmt.executeQuery();
            List<String> users = new ArrayList<String>();
            while (rs.next()){
                User user = EntityMapper.mapRsToClass(rs, User.class);
                String id= user.getDni();
                users.add(id);
            }
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}