package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.TaskDao;
import com.genome.parpalak.dao.model.Pager;
import com.genome.parpalak.dao.model.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.genome.parpalak.dao.dao.impl.queries.TaskDaoQuery.*;
import com.genome.parpalak.dao.dto.StoryDto;
import com.genome.parpalak.dao.utils.EntityManager;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDaoImpl implements TaskDao {

    Pager pager;
    private static final Logger LOGGER = Logger.getLogger(MessageDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();

    public TaskDaoImpl(Pager pager) {
        this.pager = pager;
    }

    public TaskDaoImpl() {}

    @Override
    public void save(Task task) {
        if (task == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null task");
            throw new IllegalArgumentException("Can't save null task");
        }
        Object[] args = new Object[] {task.getName(), task.getProjectId()};
        int affected = entityManager.updateData(MERGE_TASK_OBJECT, args);
        LOGGER.log(Level.INFO, "Tsk was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Task task) {
        if (task == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null task");
            throw new IllegalArgumentException("Can't delete null task");
        }
        int affected = entityManager.deleteData(DELETE_TASK, task.getId());
        LOGGER.log(Level.INFO, "Task with id " + task.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Task find(int id) {
        List<Task> tasks = entityManager.selectData(FIND_TASK_BY_ID, new TaskMapper(), id);
        Task task = tasks.get(0);
        if (task.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Task with id {0}", id);
            return task;
        } else {
            LOGGER.log(Level.INFO, "Found null Task");
            return task;
        }
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = entityManager.selectData(FIND_ALL_TASKS, new TaskMapper());
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    @Override
    public void updateTaskTerm(int taskId, Date term) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] args = new Object[] {sdf.format(term), taskId};
        int affected = entityManager.updateData(UPDATE_TASK_TERM, args);
        LOGGER.log(Level.INFO, "Task was saved, affected {0} rows", affected);
    }
    
    @Override
    public void updateDescription(String description, int taskId) {
        Object[] args = new Object[] {description, taskId};
        int affected = entityManager.updateData(UPDATE_DESCRIPTION, args);
        LOGGER.log(Level.INFO, "Task was saved, affected {0} rows", affected);
    }
    
    @Override
    public void updateTasksWithSprint(int sprintId, List<Task> list) {
        for (Task task : list) {
            Object[] args = new Object[] {sprintId, task.getId()};
            int affected = entityManager.updateData(UPDATE_TASK_WITH_SPRINT, args);
            LOGGER.log(Level.INFO, "Task with id " + task.getId() + " was updated, affected {0} rows", affected);
        }
    }
    
    @Override
    public void moveThroughBoards(int taskId, int boardId) {
        Object[] fields = new Object[] {boardId, taskId};
        int affected = entityManager.updateData(MOVE_TASK_THROUGH_BOARDS, fields);
        LOGGER.log(Level.INFO, "Task was saved, affected {0} rows", affected);
    }
    
    @Override
    public List<StoryDto> fillStory(int selectedStoryId) {

        ArrayList<StoryDto> storyList = new ArrayList<>();

        Object[] fields = new Object[] {selectedStoryId, selectedStoryId};
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_STORY_BY_ID, new String[]{"TASK_ID", "NAME", "DATETIME", "DESCRIPTION", "BOARD", "PARTICIPANT_NAME"}, fields);
        for (ArrayList list : listOfLists) {
            StoryDto storyDto = new StoryDto();
            storyDto.setId(Integer.valueOf(list.get(0).toString()));
            storyDto.setName(list.get(1).toString());
            if (list.get(2) != "") {
                storyDto.setDatetime((Date) list.get(2));
            } else {
                storyDto.setDatetime(null);
            }
            storyDto.setDescription(list.get(3).toString());
            storyDto.setBoard(list.get(4).toString());
            String participant = list.get(5).toString();
            if (participant.equals("")) {
                storyDto.setParticipantName(null);
            } else {
                storyDto.setParticipantName(participant);
            }
            storyList.add(storyDto);
        }

        return storyList;
    }
    
    @Override
    public List<Task> fillTasksBySearch(int selectedProjectId, String search) {
        Object[] fields = new Object[] {selectedProjectId, "%" + search.toLowerCase() + "%"};
        String SQL = FIND_TASKS_BY_SEARCH + getSqlForPager(FIND_TASKS_BY_SEARCH, fields);
        List<Task> tasks = entityManager.selectData(SQL, new TaskMapper(), fields);
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    @Override
    public List<Task> fillTasksAll(int selectedProjectId) {
        String SQL = FIND_TASKS_ALL_BY_PROJECT + getSqlForPager(FIND_TASKS_BY_PROJECT, selectedProjectId);
        List<Task> tasks = entityManager.selectData(SQL, new TaskMapper(), selectedProjectId);
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    @Override
    public List<Task> fillTasksByProject(int selectedProjectId) {
        String SQL = FIND_TASKS_BY_PROJECT + getSqlForPager(FIND_TASKS_BY_PROJECT, new Object[]{selectedProjectId});
        List<Task> tasks = entityManager.selectData(SQL, new TaskMapper(), selectedProjectId);
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    @Override
    public List<Task> fillTasksByProjectWithoutPager(int selectedProjectId) {
        List<Task> tasks = entityManager.selectData(FIND_TASKS_BY_PROJECT, new TaskMapper(), selectedProjectId);
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    private String getSqlForPager(String sql, Object ... fields) {
        List<Task> tasks = entityManager.selectData(sql, new TaskMapper(), fields);
        int total = tasks.size();
        StringBuilder sqlBuilder = new StringBuilder("");
        
        pager.setTotalTasksCount(total);
        int tasksOnPage = pager.getTasksCountOnPage();

        if (total > pager.getTasksCountOnPage()) {
            sqlBuilder.append(" limit ").append(pager.getSelectedPageNumber() * tasksOnPage - tasksOnPage).append(",").append(tasksOnPage);
        }

        return sqlBuilder.toString();
    }
    
    @Override
    public List<Task> getTasksBySprint(int sprintId, int projectId) {
        Object[] fields = new Object[] {projectId, sprintId};
        List<Task> tasks = entityManager.selectData(FIND_TASKS_BY_SPRINT, new TaskMapper(), fields);
        LOGGER.log(Level.INFO, "Found {0} task objects", tasks.size());
        return tasks;
    }
    
    class TaskMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getInt("TASK_ID"));
            task.setName(rs.getString("NAME"));
            task.setDatetime(rs.getDate("DATETIME"));
            task.setDescription(rs.getString("DESCRIPTION"));
            task.setBoardId(rs.getInt("BOARD_ID"));
            task.setProjectId(rs.getInt("PROJECT_ID"));
            task.setSprintId(rs.getInt("SPRINT_ID"));
            task.setParticipantId(rs.getInt("PARTICIPANT_ID"));
            return task;
        }
        
    }

}
