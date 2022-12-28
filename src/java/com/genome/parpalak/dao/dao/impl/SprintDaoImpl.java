package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.SprintDao;
import com.genome.parpalak.dao.model.Sprint;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.SprintDaoQuery.*;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SprintDaoImpl implements SprintDao {
    
    private static final Logger LOGGER = Logger.getLogger(SprintDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();

    public SprintDaoImpl() {}

    private int getMaxSprintId() {
        ArrayList<ArrayList> listOfLists = entityManager.selectData(MAX_SPRINT_ID, new String[]{"max_sprint_id"});
        for (ArrayList list : listOfLists) {
            int maxSprintId = Integer.valueOf(list.get(0).toString());
            LOGGER.log(Level.INFO, "Max sprint id is {0}", maxSprintId);
            return maxSprintId;
        }
        return 0;
    }

    @Override
    public void save(Sprint sprint) {
        if (sprint == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null sprint");
            throw new IllegalArgumentException("Can't save null sprint");
        }
        Object[] args = new Object[] {sprint.getName(), sprint.getStartDateString(), sprint.getEndDateString(), sprint.getProjectId()};
        int affected = entityManager.updateData(MERGE_SPRINT_OBJECT, args);
        LOGGER.log(Level.INFO, "Sprint was saved, affected {0} rows", affected);
        
        int maxSprintId = getMaxSprintId();

        new TaskDaoImpl().updateTasksWithSprint(maxSprintId, sprint.getSprintTasks());    
    }

    @Override
    public void delete(Sprint sprint) {
        if (sprint == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null sprint");
            throw new IllegalArgumentException("Can't delete null sprint");
        }
        int affected = entityManager.updateData(RELEASE_TASKS_FROM_SPRINT);
        LOGGER.log(Level.INFO, "Tasks from sprint with id " + sprint.getId() + " was released, affected {0} rows", affected);
        
        affected = entityManager.deleteData(DELETE_SPRINT);
        LOGGER.log(Level.INFO, "Sprint with id " + sprint.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Sprint find(int id) {
        List<Sprint> sprints = entityManager.selectData(FIND_SPRINT_BY_ID, new SprintMapper(), id);
        Sprint sprint = sprints.get(0);
        if (sprint.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Sprint with id {0}", id);
            return sprint;
        } else {
            LOGGER.log(Level.INFO, "Found null Sprint");
            return sprint;
        }
    }

    @Override
    public List<Sprint> getAll() {
        List<Sprint> sprints = entityManager.selectData(FIND_ALL_SPRINTS, new SprintMapper());
        LOGGER.log(Level.INFO, "Found {0} sprint objects", sprints.size());
        return sprints;
    }
    
    @Override
    public List<Sprint> getSprintsByProject(int projectId) {
        List<Sprint> sprints = entityManager.selectData(FIND_SPRINTS_BY_PROJECT, new SprintMapper(), projectId);
        LOGGER.log(Level.INFO, "Found {0} sprint objects", sprints.size());
        return sprints;
    }
    
    @Override
    public boolean isSprintDone(int sprintId) {
        if(isSprintCompleted(sprintId))
            return false;

        ArrayList<ArrayList> listOfLists = entityManager.selectData(COUNT_OF_SPRINTS, new String[]{"count_sprint"}, new Object[] {sprintId});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString()) == 0;
        }
        return false;
    }
    
    @Override
    public boolean isSprintCompleted(int sprintId) {
        ArrayList<ArrayList> listOfLists = entityManager.selectData(IS_SPRINT_COMPLETED, new String[]{"completed"}, new Object[] {sprintId});
        for (ArrayList list : listOfLists) {
            return Integer.valueOf(list.get(0).toString()) == 1;
        }
        return false;
    }
    
    @Override
    public void finishSprint(int sprintId) {
        int affected = entityManager.updateData(FINISH_SPRINT);
        LOGGER.log(Level.INFO, "Sprint with id " + sprintId + " was finished, affected {0} rows", affected);
    }
    
    class SprintMapper implements RowMapper<Sprint> {

        @Override
        public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sprint sprint = new Sprint();
            sprint.setId(rs.getInt("SPRINT_ID"));
            sprint.setName(rs.getString("NAME"));
            sprint.setStartDate(rs.getDate("START"));
            sprint.setEndDate(rs.getDate("END"));
            sprint.setProjectId(rs.getInt("PROJECT_ID"));
            Integer lastSprintInWork = rs.getInt("LAST_SPRINT_IN_WORK");
            Integer completed = rs.getInt("COMPLETED");
            sprint.setLastSprintInWork(lastSprintInWork == 1 || completed == 1);
            sprint.setCompleted(completed == 1);
            return sprint;
        }
        
    }

}
