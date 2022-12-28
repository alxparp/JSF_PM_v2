package com.genome.parpalak.dao.model;

public class Comment extends Model {
    
    private String description;
    private Integer participantId;
    private Integer taskId;

    public Comment() {
    }
    
    public Comment(String description, Integer participantId, Integer taskId) {
        this.description = description;
        this.participantId = participantId;
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }
    
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    @Override
    public String toString() {
        return "Comment {" +
                "id=" + getId() +
                "description='" + description + '\'' +
                '}';
    }

}
