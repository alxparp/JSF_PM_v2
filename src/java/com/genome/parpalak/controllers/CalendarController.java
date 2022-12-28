package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.dto.CalendarDto;
import com.genome.parpalak.dao.model.Task;
import com.genome.parpalak.services.CalendarService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class CalendarController implements Serializable {
    
    private List<CalendarDto> calendarList;
    
    @Inject
    private CalendarService calendarService;

    @PostConstruct
    public void init() {
        calendarList = new ArrayList<>();
        fillCalendar();
    }

    public void fillCalendar() {
        List<Task> taskNameList = (List<Task>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tasksNameList");
        if(taskNameList == null) 
            return;
        calendarList = calendarService.getCalendarWithTasks(taskNameList);
    }

    public List<CalendarDto> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(ArrayList<CalendarDto> calendarList) {
        this.calendarList = calendarList;
    }

}
