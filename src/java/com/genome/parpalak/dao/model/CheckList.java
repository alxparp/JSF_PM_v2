package com.genome.parpalak.dao.model;

import java.util.List;

public class CheckList extends Model {

    private String name;
    private List<Item> items;
    private Integer taskId;

    public CheckList() {
    }
    
    public CheckList(String name, Integer taskId) {
        this.name = name;
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public Integer getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    @Override
    public String toString() {
        return "CheckList {" +
                "id=" + getId() +
                "name='" + name + '\'' +
                '}';
    }
    
}
