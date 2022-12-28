package com.genome.parpalak.dao.model;

import java.util.Date;

public class Message extends Model {
    
    private String description;
    private Date datetime;
    private Integer participantId;
    private int projectId;
    
    public Message() {}
    
    public Message(String description, Integer participantId, Integer projectId) {
        this.description = description;
        this.participantId = participantId;
        this.projectId = projectId;
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

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }
    
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    @Override
    public String toString() {
        return "Message {" +
                "id=" + getId() +
                "description='" + description + '\'' +
                "datetime='" + datetime + '\'' +
                '}';
    }
     
}
