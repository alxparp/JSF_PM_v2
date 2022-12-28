package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dto.CalendarDto;
import com.genome.parpalak.dao.model.Task;
import com.genome.parpalak.services.CalendarService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {
    
    @Override
    public List<CalendarDto> getCalendarWithTasks(List<Task> taskNameList) {
        
        List<CalendarDto> calendarList = new ArrayList<>();
        
        for (int i = 1; i <= getNumberDaysInMonth(); i++) {
            CalendarDto calendar = new CalendarDto();
            calendar.setDay(i);
            List<Task> taskList = new ArrayList<>();
            for (Task task : taskNameList) {

                Date date = task.getDatetime();
                if (date != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    if (day == i) {
                        taskList.add(task);
                    }
                }
            }
            calendar.setTaskList(taskList);
            calendarList.add(calendar);
        }
        
        return calendarList;
    }
    
    private int getNumberDaysInMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        int days = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return days;
    }
    
}
