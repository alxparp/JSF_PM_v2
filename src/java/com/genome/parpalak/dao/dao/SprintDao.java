package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.Sprint;
import java.util.List;

public interface SprintDao {
    
    void save(Sprint sprint);
    void delete (Sprint sprint);
    Sprint find(int id);
    List<Sprint> getAll();
    List<Sprint> getSprintsByProject(int projectId);
    boolean isSprintDone(int sprintId);
    boolean isSprintCompleted(int sprintId);
    void finishSprint(int sprintId);
    
}
