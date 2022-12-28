package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.Pager;
import com.genome.parpalak.dao.dto.StoryDto;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.Participant;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ParticipantService;
import com.genome.parpalak.services.TaskService;
import com.genome.parpalak.services.impl.TaskServiceImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
public class StoryController implements Serializable {

    @Inject
    private TaskService taskService;
    
    @Inject
    private ParticipantService participantService;
    
    @Inject
    private HistoryService historyService;
    
    private Pager pager;
    private StoryDto currentStory;
    private Project project;
    private int selectedStoryId;
    private final Map<String, Integer> searchList = new HashMap<>();
    private List<Participant> currentParticipantList;
    private static final Logger LOGGER = Logger.getLogger(StoryController.class.getName());
    
    @PostConstruct
    public void init() {
        pager = new Pager();
        taskService = new TaskServiceImpl(pager);
        selectedStoryId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedStoryId");
        project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        currentStory = taskService.fillStory(selectedStoryId).get(0);
        currentParticipantList = participantService.findParticipantsByProject(project.getId());
        LOGGER.log(Level.INFO, "Story Controller has been initialized...");
    }
    
    private void fillStory() {
        currentStory = taskService.fillStory(selectedStoryId).get(0);
    }
    
    private void saveHistory(String historyDescription) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        History history = new History(historyDescription, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history);
    }
    
    public void deleteParticipantFromStory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        participantService.deleteParticipantFromStory(params.get("task_id"));
        String storyName = params.get("task_name");
        String description = " удалил участника из карточки \"" + storyName + "\"";
        saveHistory(description);
        fillStory();
    } 
    
    // получение списка участников к проекту претендующих на карточку
    public Map<String, Integer> getSearchList() {
        fillSearchList();
        return searchList;
    }
    
    // заполнение списка участников к проекту претендующих на карточку
    private void fillSearchList() {
        searchList.clear();
        for (Participant part : currentParticipantList) {
            searchList.put(part.getName(), part.getId());
        }
    }
    
    // изменить участника задачи
    public void searchTypeChanged(ValueChangeEvent e) {
        int participantId = Integer.parseInt(e.getNewValue().toString());  
        participantService.setParticipantToStory(participantId, selectedStoryId);
        String description = " изменил/добавил участника к карточке ";
        saveHistory(description);
        currentStory = taskService.fillStory(selectedStoryId).get(0);
    }

    public void updateTerm() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedStoryId = Integer.valueOf(params.get("task_id"));
        Date term = currentStory.getDatetime();
        taskService.updateTaskTerm(selectedStoryId, term);
        String description = " добавил/изменил срок для карточки \"" + currentStory.getName() + "\"";
        saveHistory(description);
        fillStory();
    }

    public void updateDescription() {
        taskService.updateDescription(currentStory.getDescription(), selectedStoryId);
        String description = " обновил описание карточки \"" + currentStory.getName() + "\"";
        saveHistory(description);
        fillStory();
    }

    public StoryDto getCurrentStory() {
        return currentStory;
    }

    public void setCurrentStory(StoryDto currentStory) {
        this.currentStory = currentStory;
    }

    public int getSelectedStoryId() {
        return selectedStoryId;
    }

    public void setSelectedStoryId(int selectedStoryId) {
        this.selectedStoryId = selectedStoryId;
    }
    
    

}
