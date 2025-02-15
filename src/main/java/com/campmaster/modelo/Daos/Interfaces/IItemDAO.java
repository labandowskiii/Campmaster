package com.campmaster.modelo.Daos.Interfaces;

import com.campmaster.modelo.Entities.Item;

public interface IItemDAO {
    public boolean create(Item objeto);
    public Object read(int idObject);
    public boolean update(Item objeto);
    public boolean delete(int idObject);
}
