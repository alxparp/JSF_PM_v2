package com.genome.parpalak.services;

import com.genome.parpalak.dao.dto.StoryDto;
import com.genome.parpalak.dao.model.Task;
import java.util.Date;
import java.util.List;

public interface TaskService {
    
    void save(Task task);
    void delete (Task task);
    Task find(int id);
    List<Task> getAll();
    void updateTaskTerm(int taskId, Date term);
    void updateDescription(String description, int taskId);
    void updateTasksWithSprint(int sprintId, List<Task> list);
    void moveThroughBoards(int taskId, int boardId);
    List<StoryDto> fillStory(int selectedStoryId);
    List<Task> fillTasksBySearch(int selectedProjectId, String search);
    List<Task> fillTasksAll(int selectedProjectId);
    List<Task> fillTasksByProject(int selectedProjectId);
    List<Task> fillTasksByProjectWithoutPager(int selectedProjectId);
    List<Task> getTasksBySprint(int sprintId, int projectId);
    
}
