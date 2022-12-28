package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.Project;
import java.util.List;

public interface ProjectDao {
    
    void save(Project project);
    void delete (Project project);
    Project find(int id);
    List<Project> getAll();
    List<Project> findProjectsByUsername(String username);
    
}
