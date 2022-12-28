package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.SprintDao;
import com.genome.parpalak.dao.model.Sprint;
import com.genome.parpalak.services.SprintService;
import java.util.List;
import javax.inject.Inject;

public class SprintServiceImpl implements SprintService {

    @Inject
    private SprintDao sprintDao;
    
    SprintServiceImpl() {}
    
    @Override
    public void save(Sprint sprint) {
        sprintDao.save(sprint);
    }

    @Override
    public void delete(Sprint sprint) {
        sprintDao.delete(sprint);
    }

    @Override
    public Sprint find(int id) {
        return sprintDao.find(id);
    }

    @Override
    public List<Sprint> getAll() {
        return sprintDao.getAll();
    }

    @Override
    public List<Sprint> getSprintsByProject(int projectId) {
        return sprintDao.getSprintsByProject(projectId);
    }

    @Override
    public boolean isSprintDone(int sprintId) {
        return sprintDao.isSprintDone(sprintId);
    }

    @Override
    public boolean isSprintCompleted(int sprintId) {
        return sprintDao.isSprintCompleted(sprintId);
    }

    @Override
    public void finishSprint(int sprintId) {
        sprintDao.finishSprint(sprintId);
    }
    
}
