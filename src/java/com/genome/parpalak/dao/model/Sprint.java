package com.genome.parpalak.dao.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sprint extends Model {
    
    private boolean lastSprintInWork;
    private String name;
    private Date startDate;
    private Date endDate;
    private Integer projectId;
    private List<Task> sprintTasks;
    private boolean completed;
    
    public Sprint() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Task> getSprintTasks() {
        return sprintTasks;
    }

    public void setSprintTasks(List<Task> sprintTasks) {
        this.sprintTasks = sprintTasks;
    }
    
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getStartDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(startDate);
    }
    
    public String getEndDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
    }

    public boolean isLastSprintInWork() {
        return lastSprintInWork;
    }

    public void setLastSprintInWork(boolean lastSprintInWork) {
        this.lastSprintInWork = lastSprintInWork;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    @Override
    public String toString() {
        return "Sprint {" +
                "id=" + getId() +
                "name='" + name + '\'' +
                "startDate='" + startDate + '\'' +
                "endDate='" + endDate + '\'' +
                "lastSprintInWork='" + lastSprintInWork + '\'' +
                "completed='" + completed + '\'' +
                '}';
    }
 
}
