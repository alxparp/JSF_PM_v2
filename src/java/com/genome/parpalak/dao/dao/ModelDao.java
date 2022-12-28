package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.Model;
import java.io.Serializable;
import java.util.List;

public interface ModelDao<T extends Model> extends Serializable {
    
    void save(T o);
    void delete (T o);
    T find(int id);
    List<T> getAll();
    
}
