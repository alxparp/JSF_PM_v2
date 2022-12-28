package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.dao.impl.BoardDaoImpl;
import com.genome.parpalak.dao.model.Sprint;
import com.genome.parpalak.dao.model.Task;
import com.genome.parpalak.dao.dao.impl.TaskDaoImpl;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ParticipantService;
import com.genome.parpalak.services.SprintService;
import com.genome.parpalak.services.TaskService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public final class SprintController {

    @Inject
    private SprintService sprintService;
    
    @Inject
    private TaskService taskService;
    
    @Inject
    private HistoryService historyService;
    
    @Inject
    private ParticipantService participantService;
    
    private String name;
    private Date startDate;
    private Date endDate;
    private Sprint sprint;
    private List<Task> currentTasks;
    private List<Task> tasksNameList;
    private boolean sprintIsDone;
    private List<Sprint> currentSprints;
    private final Map<String, Integer> searchList = new HashMap<>();
    private Sprint currentSprintView;
    private int sprintNumber = 0;

    @PostConstruct
    public void init() {
        currentTasks = new ArrayList<>();
        fillSprints();
        fillSearchList();
        if(currentSprints.size() > 0) {
            fillTasksBySprint();
            ifSprintIsDone();
        }
    }
    
    public String goToCalendar() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tasksNameList", tasksNameList);
        return "calendar";
    }
    
    public String addSprint() {
        ifSprintIsDone();
        return "sprint";
    }

    public void fillSprints() {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        currentSprints = sprintService.getSprintsByProject(project.getId());
    }
    
    public void ifSprintIsDone() {
        if(!currentSprints.isEmpty())
            sprintIsDone = sprintService.isSprintDone(currentSprints.get(sprintNumber).getId());
        else
            sprintIsDone = false;
    }
    
    public boolean ifSprintIsCompleted() {
        if(!currentSprints.isEmpty())
            return sprintService.isSprintCompleted(currentSprints.get(sprintNumber).getId());
        return false;
    }
    
    public void fillTasksBySprint() {
        if(!currentSprints.isEmpty()) {
            Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
            tasksNameList = taskService.getTasksBySprint(currentSprints.get(sprintNumber).getId(), project.getId());
        }
    }
    
    public void finishSprint() {
        sprintService.finishSprint(currentSprints.get(sprintNumber).getId());
        ifSprintIsDone();
    }
    
    private void saveHistory(String historyDescription) {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        History history = new History(historyDescription, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history); 
    }
    
    private void moveThroughBoards(int taskId, String currentBoard, int tableId, BoardDaoImpl boardDaoImpl) {
        new TaskDaoImpl().moveThroughBoards(taskId, tableId);
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskName = params.get("task_name");
        String nextTableName = boardDaoImpl.find(tableId).getName();
        String description = " переместил задание \"" + taskName + "\" из таблицы \"" + currentBoard + "\"  в таблицу \"" + nextTableName + "\"";
        saveHistory(description);
        fillTasksBySprint();
        ifSprintIsDone();
    }
    
    public void moveRight() {
        BoardDaoImpl boardDaoImpl = new BoardDaoImpl();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int taskId = Integer.valueOf(params.get("task_id"));
        int tableId = Integer.valueOf(params.get("table_id"))+1;
        String boardName = boardDaoImpl.find(tableId).getName();
        moveThroughBoards(taskId, boardName, tableId, boardDaoImpl);
    }
    
    public void moveLeft() {
        BoardDaoImpl boardDaoImpl = new BoardDaoImpl();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int taskId = Integer.valueOf(params.get("task_id"));
        int tableId = Integer.valueOf(params.get("table_id"))-1;
        String boardName = boardDaoImpl.find(tableId+1).getName();
        moveThroughBoards(taskId, boardName, tableId, boardDaoImpl);
    }

    public String newSprint() {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        List<Task> currentTasksList = taskService.fillTasksByProjectWithoutPager(project.getId());
        for (Task task : currentTasksList) {
            if (task.isEdit()) {
                currentTasks.add(task);
            }
        }
        if (currentTasks.isEmpty()) {
            return "";
        }
        Sprint sprint = new Sprint();
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
        sprint.setName(name);
        sprint.setProjectId(project.getId());
        sprint.setSprintTasks(currentTasks);
        sprintService.save(sprint);
        String description = " добавил новую итерацию \"" + sprint.getName() + "\"";
        saveHistory(description);
        fillSprints();
        return "sprints.xhtml";
    }

    public void deleteSprint() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String sprintName = params.get("sprint_name");
        sprintService.delete(sprint);
        String description = " удалил итерацию \"" + sprintName + "\"";
        saveHistory(description);
        fillSprints();
    }
    
    // изменить спринт
    public void searchTypeChanged(ValueChangeEvent e) {
        int sprintId = Integer.parseInt(e.getNewValue().toString());
        int number = 0;
        for(Sprint sprintVar: currentSprints) {
            if(sprintVar.getId() == sprintId){
                number = currentSprints.indexOf(sprintVar);
            }
        }
        setSprintNumber(number);
        ifSprintIsDone();
        fillTasksBySprint();
    }
    
    public Map<String, Integer> getSearchList() {
        return searchList;
    }
    
    // заполнение списка участников к проекту претендующих на карточку
    public void fillSearchList() {
        searchList.clear();
        for (int i = currentSprints.size()-1; i >= 0 ; i--) {
            searchList.put(currentSprints.get(i).getName(), currentSprints.get(i).getId());
        }
    }
    
    public Sprint getSprint() {
        return sprint;
    }
    
    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public List<Sprint> getCurrentSprints() {
        return currentSprints;
    }

    public void setCurrentSprints(ArrayList<Sprint> currentSprints) {
        this.currentSprints = currentSprints;
    } 

    public List<Task> getTasksNameList() {
        return tasksNameList;
    }

    public void setTasksNameList(ArrayList<Task> tasksNameList) {
        this.tasksNameList = tasksNameList;
    }

    public boolean isSprintIsDone() {
        return sprintIsDone;
    }

    public void setSprintIsDone(boolean sprintIsDone) {
        this.sprintIsDone = sprintIsDone;
    }

    public Sprint getCurrentSprintView() {
        return currentSprintView;
    }

    public void setCurrentSprintView(Sprint currentSprintView) {
        this.currentSprintView = currentSprintView;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }   
    

}
