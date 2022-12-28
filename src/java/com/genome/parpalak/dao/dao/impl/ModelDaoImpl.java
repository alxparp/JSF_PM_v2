package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.ModelDao;
import com.genome.parpalak.dao.model.Model;
import java.util.List;


public class ModelDaoImpl<T extends Model> implements ModelDao<T> {
    
    
    @Override
    public void save(T o) {
    }

    @Override
    public void delete(T o) {
        
    }

    @Override
    public T find(int id) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

   
    
}
