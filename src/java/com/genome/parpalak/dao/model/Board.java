package com.genome.parpalak.dao.model;

public class Board extends Model {

    private String name;

    public Board() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Board {" +
                "id=" + getId() +
                "name='" + name + '\'' +
                '}';
    }
   
}
