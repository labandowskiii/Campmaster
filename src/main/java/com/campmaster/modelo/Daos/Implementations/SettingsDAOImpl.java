package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.ISettingsDAO;
import com.campmaster.modelo.connections.MainDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.mindrot.jbcrypt.BCrypt;

public class SettingsDAOImpl implements ISettingsDAO {

    private final MainDB mainDB;
    static Connection c;

    public SettingsDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }


    public String read(String key) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM configuracion WHERE clave = ?");
            stmt.setString(1, key);
            stmt.executeQuery();
            stmt.getResultSet().next();
            return stmt.getResultSet().getString("valor");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateMonitPw(String pwtype) {
        return BCrypt.checkpw(pwtype, read("monit_pw"));
    }

    public boolean validateAdminPw(String pwtype) {
        return BCrypt.checkpw(pwtype, read("admin_pw"));
    }
}
