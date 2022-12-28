/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genome.parpalak.dao.dto;

import java.io.Serializable;
import java.util.Date;


public class MessageDto implements Serializable {
    
    private Integer id;
    private String description;
    private Date datetime;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String project) {
        this.participant = project;
    }
    
}
