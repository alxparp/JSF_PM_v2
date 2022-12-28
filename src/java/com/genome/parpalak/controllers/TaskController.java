package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.Pager;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.Task;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ParticipantService;
import com.genome.parpalak.services.TaskService;
import com.genome.parpalak.services.impl.TaskServiceImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public final class TaskController implements Serializable {

    @Inject
    private TaskService taskService;
    
    @Inject
    private HistoryService historyService;
    
    @Inject
    private ParticipantService participantService;
    
    private String search;
    private String story;
    private List<Task> currentTaskList;
    private List<Task> currentTaskListWithoutPager;
    private int selectedProjectId = 1;
    private int selectedStoryId;
    private String taskParticipant;
    private Pager pager;
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());
    
    @PostConstruct
    public void init() {
        selectedStoryId = 0;
        pager = new Pager();
        taskService = new TaskServiceImpl(pager);
        fillTasksAll();
        fillReallyTasksAll();
        LOGGER.log(Level.INFO, "Task Controller has been initialized...");
    }
    
    public String goToStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedStoryId", selectedStoryId);
        return "story";
    }
    
    public void fillReallyTasksAll() {
        currentTaskListWithoutPager = taskService.fillTasksByProjectWithoutPager(selectedProjectId);
    }
    
    // заполнить список задачами из БД
    private void fillTasksAll() {
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
    }

    // найденные задачи
    public String fillTasksBySearch() {
        if (search.trim().length() == 0) {
            fillTasksAll();
            return "/pages/main.xhtml";
        }
        currentTaskList = taskService.fillTasksBySearch(selectedProjectId, search);
        pager.setSelectedPageNumber(1);
        return "/pages/main.xhtml";
    }

    // задачи, которые находяться в проекте
    public void fillTasksByProject() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedProjectId = Integer.valueOf(params.get("project_id"));
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
        pager.setSelectedPageNumber(1);

    }
    
    // количество задач на странице
    public void tasksOnPageChanged(ValueChangeEvent e) {
        pager.setTasksCountOnPage(Integer.valueOf(e.getNewValue().toString()));
        pager.setSelectedPageNumber(1);
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
    }

    // выбор страницы
    public String selectPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        pager.setSelectedPageNumber(Integer.valueOf(params.get("page_number")));
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
        return "tasks";
    }

    // заполнить карточку данными
    public String fillStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));
        taskService.fillStory(selectedStoryId);
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
        pager.setSelectedPageNumber(1);
        return "story";
    }
    
    private void saveHistory(String historyDescription) {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        History history = new History(historyDescription, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history);
    }

    // удаление карточки
    public void deleteStory() {
        Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskId = param.get("task_id");
        String taskName = param.get("task_name");
        Task task = new Task();
        task.setId(Integer.valueOf(taskId));
        taskService.delete(task);
        String description = "удалил карточку \"" + taskName + "\"";
        saveHistory(description);
        currentTaskList = taskService.fillTasksAll(selectedProjectId);
    }

    // добавить новую карточку
    public void addNewStory() {
        if (story.trim().length() == 0) {
            return;
        }

        Task task = new Task();
        task.setProjectId(selectedProjectId);
        task.setName(story);        
        taskService.save(task);
        
        String description = " добавил карточку \"" + story + "\"";
        saveHistory(description);
        currentTaskList = taskService.fillTasksByProject(selectedProjectId);
        story="";
        pager.setSelectedPageNumber(1);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Task> getCurrentTaskList() {
        return currentTaskList;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(int selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
    }

    public String getTaskParticipant() {
        return taskParticipant;
    }

    public void setTaskParticipant(String taskParticipant) {
        this.taskParticipant = taskParticipant;
    }

    public int getSelectedStoryId() {
        return selectedStoryId;
    }

    public void setSelectedStoryId(int selectedStoryId) {
        this.selectedStoryId = selectedStoryId;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Task> getCurrentTaskListWithoutPager() {
        return currentTaskListWithoutPager;
    }

    public void setCurrentTaskListWithoutPager(ArrayList<Task> currentTaskListWithoutPager) {
        this.currentTaskListWithoutPager = currentTaskListWithoutPager;
    }
    
}
