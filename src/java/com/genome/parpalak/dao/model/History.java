package com.genome.parpalak.dao.model;

import java.util.Date;


public class History extends Model {
    
    private String description;
    private Date datetime;
    private Integer projectId;
    private Integer participantId;

    public History() {}
    
    public History(String description, Integer projectId, Integer participantId) {
        this.description = description;
        this.projectId = projectId;
        this.participantId = participantId;
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
    
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }
    
    @Override
    public String toString() {
        return "History {" +
                "id=" + getId() +
                "description='" + description + '\'' +
                "datetime='" + datetime + '\'' +
                '}';
    }
     
}
