package com.campmaster.modelo.Daos.Interfaces;

import com.campmaster.modelo.Entities.Document;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public interface IDocumentDAO {
    /**
     * This method creates an object in the database
     * @param document
     * @return
     */
    public boolean create(Document document);

    /**
     * This method reads an object from the database
     *
     * @param ID
     * @return
     */
    public Document read(String ID);


    /**
     * This method updates an object in the database
     *
     * @param document
     * @return
     */
    Document update(Document document);
}
