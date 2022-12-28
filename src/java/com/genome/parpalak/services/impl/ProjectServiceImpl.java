package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.ProjectDao;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.services.ProjectService;
import java.util.List;
import javax.inject.Inject;

public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectDao projectDao;
    
    ProjectServiceImpl() {}
    
    @Override
    public void save(Project project) {
        projectDao.save(project);
    }

    @Override
    public void delete(Project project) {
        projectDao.delete(project);
    }

    @Override
    public Project find(int id) {
        return projectDao.find(id);
    }

    @Override
    public List<Project> getAll() {
        return projectDao.getAll();
    }

    @Override
    public List<Project> findProjectsByUsername(String username) {
        return projectDao.findProjectsByUsername(username);
    }
    
}
