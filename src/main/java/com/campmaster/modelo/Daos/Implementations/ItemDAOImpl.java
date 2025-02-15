package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.IItemDAO;
import com.campmaster.modelo.Entities.Item;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.extra.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements IItemDAO {

    private final MainDB mainDB;
    Connection c;

    public ItemDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }

    @Override
    public boolean create(Item objeto) {
        PreparedStatement stmt=null;
        try {
            stmt=c.prepareStatement("INSERT INTO objetos(nombre, marca, cantidad) VALUES (?, ?, ?)");
            stmt.setString(1,objeto.getNombre());
            stmt.setString(2, objeto.getMarca());
            stmt.setInt(3, objeto.getCantidad());
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Item read(int idObject) {
        PreparedStatement stmt=null;
        ResultSet rs=null;
        Item objeto=null;
        try{
            stmt=c.prepareStatement("SELECT * FROM objetos WHERE id=?");
            stmt.setInt(1,idObject);
            rs=stmt.executeQuery();

            if (rs.next()){
                objeto=EntityMapper.mapRsToClass(rs, Item.class);
            }
            return objeto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Item objeto) {
        PreparedStatement stmt=null;
        try{
            stmt=c.prepareStatement("UPDATE objetos SET nombre=?, marca=?, cantidad=? WHERE id=?");
            stmt.setString(1, objeto.getNombre());
            stmt.setString(2, objeto.getMarca());
            stmt.setInt(3,objeto.getCantidad());
            stmt.setInt(4, objeto.getId());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int idObject) {
        PreparedStatement stmt=null;
        try{
            stmt=c.prepareStatement("DELETE FROM objetos WHERE id=?");
            stmt.setInt(1,idObject);
            stmt.executeQuery();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> readAll() {
        PreparedStatement stmt=null;
        ResultSet rs=null;
        List<Item> objetos=new ArrayList<>();
        try{
            stmt=c.prepareStatement("SELECT * FROM objetos");
            rs=stmt.executeQuery();
            while (rs.next()){
                objetos.add(EntityMapper.mapRsToClass(rs, Item.class));
            }
            return objetos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
