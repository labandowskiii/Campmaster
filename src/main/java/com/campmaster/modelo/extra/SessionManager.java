package com.campmaster.modelo.extra;

import com.campmaster.modelo.Daos.Implementations.UserDAOImpl;
import com.campmaster.modelo.Entities.User;

import java.util.Arrays;
import java.util.List;

public class SessionManager {

    public static User user;
    public static SessionManager instance;
    public static List<String> auxMonitorList;

    private SessionManager() {
        this.user = null;
    }

    public static SessionManager getInstance() {
        if (instance==null) {
            instance=new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void logOut() {
        this.user = null;
    }

    /**
     * Auxiliary functions for the monitor list used on the create event view
     */
    public void setAuxMonitorList(List<String> list) {
        auxMonitorList = list;
    }

    public List<String> getAuxMonitorList() {
        return auxMonitorList;
    }

    public boolean validatePermission(String... requiredTypes) {
        List<String> requiredTypesList = Arrays.asList(requiredTypes);
        return requiredTypesList.contains(user.getTipo());
    }

    public void update() {
        UserDAOImpl userDAO = new UserDAOImpl();
        user = userDAO.read(user.getDni());
    }
}