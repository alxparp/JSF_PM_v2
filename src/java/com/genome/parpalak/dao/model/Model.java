/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genome.parpalak.dao.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
    
    protected Integer id;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        Model model = (Model) obj;
        return getId() != null && getId().equals(model.getId());
    }
    
    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("Entity %s, %s", this.getClass().getName(), getId());
    }
    
}
