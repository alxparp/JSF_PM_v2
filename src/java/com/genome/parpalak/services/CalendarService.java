package com.genome.parpalak.services;

import com.genome.parpalak.dao.dto.CalendarDto;
import com.genome.parpalak.dao.model.Task;
import java.util.List;

public interface CalendarService {
    
    List<CalendarDto> getCalendarWithTasks(List<Task> taskNameList);
    
}
