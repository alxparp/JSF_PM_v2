package com.genome.parpalak.dao.dto;

import com.genome.parpalak.dao.model.Task;
import java.io.Serializable;
import java.util.List;

public class CalendarDto implements Serializable {
    
    private Integer day;
    private List<Task> taskList;

    public CalendarDto() {}

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> task) {
        this.taskList = task;
    }
    
    @Override
    public String toString() {
        return "CalendarDto {" +
                "day=" + getDay() +
                '}';
    }
    
}
