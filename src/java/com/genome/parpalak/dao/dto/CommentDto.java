package com.genome.parpalak.dao.dto;

import java.io.Serializable;


public class CommentDto implements Serializable {
    
    private Integer id;
    private String description;
    private String participant;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getParticipant() {
        return participant;
    }
    
    public void setParticipant(String participant) {
        this.participant = participant;
    }
    
}
