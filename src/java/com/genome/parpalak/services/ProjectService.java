package com.genome.parpalak.services;

import com.genome.parpalak.dao.model.Project;
import java.util.List;

public interface ProjectService {
    
    void save(Project project);
    void delete (Project project);
    Project find(int id);
    List<Project> getAll();
    List<Project> findProjectsByUsername(String username);
    
}
