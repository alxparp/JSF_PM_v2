package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.TaskDao;
import com.genome.parpalak.dao.dao.impl.TaskDaoImpl;
import com.genome.parpalak.dao.dto.StoryDto;
import com.genome.parpalak.dao.model.Pager;
import com.genome.parpalak.dao.model.Task;
import com.genome.parpalak.services.TaskService;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class TaskServiceImpl implements TaskService {

    @Inject
    private TaskDao taskDao;
    private Pager pager;
    
    TaskServiceImpl() {}
    
    public TaskServiceImpl(Pager pager) {
        this.pager = pager;
        taskDao = new TaskDaoImpl(pager);
    }
    
    @Override
    public void save(Task task) {
        taskDao.save(task);
    }

    @Override
    public void delete(Task task) {
        taskDao.delete(task);
    }

    @Override
    public Task find(int id) {
        return taskDao.find(id);
    }

    @Override
    public List<Task> getAll() {
        return taskDao.getAll();
    }

    @Override
    public void updateTaskTerm(int taskId, Date term) {
        taskDao.updateTaskTerm(taskId, term);
    }

    @Override
    public void updateDescription(String description, int taskId) {
        taskDao.updateDescription(description, taskId);
    }

    @Override
    public void updateTasksWithSprint(int sprintId, List<Task> list) {
        taskDao.updateTasksWithSprint(sprintId, list);
    }

    @Override
    public void moveThroughBoards(int taskId, int boardId) {
        taskDao.moveThroughBoards(taskId, boardId);
    }

    @Override
    public List<StoryDto> fillStory(int selectedStoryId) {
        return taskDao.fillStory(selectedStoryId);
    }

    @Override
    public List<Task> fillTasksBySearch(int selectedProjectId, String search) {
        return taskDao.fillTasksBySearch(selectedProjectId, search);
    }

    @Override
    public List<Task> fillTasksAll(int selectedProjectId) {
        return taskDao.fillTasksAll(selectedProjectId);
    }

    @Override
    public List<Task> fillTasksByProject(int selectedProjectId) {
        return taskDao.fillTasksByProject(selectedProjectId);
    }

    @Override
    public List<Task> fillTasksByProjectWithoutPager(int selectedProjectId) {
        return taskDao.fillTasksByProjectWithoutPager(selectedProjectId);
    }

    @Override
    public List<Task> getTasksBySprint(int sprintId, int projectId) {
        return taskDao.getTasksBySprint(sprintId, projectId);
    }
    
}
