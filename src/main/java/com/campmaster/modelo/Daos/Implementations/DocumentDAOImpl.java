package com.campmaster.modelo.Daos.Implementations;

import com.campmaster.modelo.Daos.Interfaces.IDocumentDAO;
import com.campmaster.modelo.connections.MainDB;
import com.campmaster.modelo.Entities.Document;
import com.campmaster.modelo.connections.StorageConnection;
import com.campmaster.modelo.extra.SessionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DocumentDAOImpl implements IDocumentDAO {

    private final MainDB mainDB;
    Connection c;

    public DocumentDAOImpl() {
        this.mainDB = MainDB.getInstance();
        this.c = mainDB.getConnection();
    }

    @Override
    public boolean create(Document document) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("INSERT INTO documentos (ID, name, URL, date, ID_Usuario) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, document.getID());
            stmt.setString(2, document.getName());
            stmt.setString(3, document.getUrl());
            stmt.setDate(4, document.getDate());
            stmt.setString(5, document.getID_Usuario());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Document read(String ID) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM documentos WHERE ID = ?");
            stmt.setString(1, ID);
            stmt.executeQuery();
            return new Document();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Document update(Document document) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("UPDATE documentos SET URL = ?, date = ? WHERE ID = ?");
            stmt.setString(1, document.getUrl());
            stmt.setDate(2, document.getDate());
            stmt.setString(3, document.getID());
            stmt.executeUpdate();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existsDocument(String docId) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement("SELECT * FROM documentos WHERE ID = ?");
            stmt.setString(1, docId);
            stmt.executeQuery();
            if (stmt.getResultSet().next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean uploadDoc(String path, String tipo) {
        StorageConnection s = new StorageConnection();
        try {
            String url=s.uploadDoc(path, SessionManager.getInstance().getUser().getDni(), tipo);
            Document doc = new Document(SessionManager.getInstance().getUser().getDni()+"-"+tipo, tipo + SessionManager.getInstance().getUser().getDni(), url, new Date(System.currentTimeMillis()), SessionManager.getInstance().getUser().getDni());
            create(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExistingDoc(Document doc, String path, String tipo) {
        StorageConnection s = new StorageConnection();
        try {
            String url=s.uploadDoc(path, SessionManager.getInstance().getUser().getDni(), tipo);
            doc.setUrl(url);
            update(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
