package com.genome.parpalak.dao.model;

public class Item extends Model {
    
    private String name;
    private Integer completed;
    private Integer checkListId;

    public Item() {}
    
    public Item(Integer id, Integer checkListId) {
        this.id = id;
        this.checkListId = checkListId;
    }
    
    public Item(String name, Integer checkListId) {
        this.name = name;
        this.checkListId = checkListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public int getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(int checkListId) {
        this.checkListId = checkListId;
    }
    
    
    
    @Override
    public String toString() {
        return "Items {" +
                "id=" + getId() +
                "name='" + name + '\'' +
                "completed='" + completed + '\'' +
                '}';
    }
    
}
