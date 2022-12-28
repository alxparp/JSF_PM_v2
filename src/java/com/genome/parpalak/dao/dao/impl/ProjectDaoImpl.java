package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.ProjectDao;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.ProjectDaoQuery.*;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDaoImpl implements ProjectDao {
    
    private static final Logger LOGGER = Logger.getLogger(ProjectDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();

    @Override
    public void save(Project project) {
        if (project == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null project");
            throw new IllegalArgumentException("Can't save null project");
        }
        Object[] args = new Object[] {project.getName()};
        int affected = entityManager.updateData(MERGE_PROJECT_OBJECT, args);
        LOGGER.log(Level.INFO, "Project was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Project project) {
        if (project == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null project");
            throw new IllegalArgumentException("Can't delete null project");
        }
        int affected = entityManager.deleteData(DELETE_PROJECT, project.getId());
        LOGGER.log(Level.INFO, "Participant with id " + project.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Project find(int id) {
        List<Project> projects = entityManager.selectData(FIND_ALL_PROJECTS, new ProjectMapper(), id);
        Project project = projects.get(0);
        if (project.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Project with id {0}", id);
            return project;
        } else {
            LOGGER.log(Level.INFO, "Found null Project");
            return project;
        }
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = entityManager.selectData(FIND_ALL_PROJECTS, new ProjectMapper());
        LOGGER.log(Level.INFO, "Found {0} project objects", projects.size());
        return projects;
    }
    
    @Override
    public List<Project> findProjectsByUsername(String username) {
        List<Project> projects = entityManager.selectData(FIND_PROJECTS_BY_USERNAME, new ProjectMapper(), username);
        LOGGER.log(Level.INFO, "Found {0} project objects", projects.size());
        return projects;
    }
    
    class ProjectMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setId(rs.getInt("PROJECT_ID"));
            project.setName(rs.getString("NAME"));
            return project;
        }
        
    }
    
}
