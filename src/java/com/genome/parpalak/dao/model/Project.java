package com.genome.parpalak.dao.model;

public class Project extends Model {

    private String name;

    public Project() {}
    
    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Project {" +
                "id=" + getId() +
                "name='" + name + '\'' +
                '}';
    }
    
}
