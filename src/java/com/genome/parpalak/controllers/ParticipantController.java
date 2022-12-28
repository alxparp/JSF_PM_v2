package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.Participant;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ParticipantService;
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
import javax.inject.Inject;


@ManagedBean
@ViewScoped
public final class ParticipantController implements Serializable {
    
    @Inject
    private ParticipantService participantService;
    
    @Inject
    private HistoryService historyService;

    private List<Participant> currentParticipantList;
    private String participant;  
    private Project project;
    private static final Logger LOGGER = Logger.getLogger(ParticipantController.class.getName());
    
    @PostConstruct
    public void init() {
        project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        fillParticipantsAll();
        LOGGER.log(Level.INFO, "Participant Controller has been initialized...");
    }
    
    public void fillParticipantsAll() {
        currentParticipantList = participantService.findParticipantsByProject(project.getId());
    }
    
    private void saveHistory(String historyDescription, Project project) {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        History history = new History(historyDescription, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history);
    }
    
    public void deleteParticipant() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();     
        participantService.deleteParticipantFromProject(Integer.valueOf(params.get("participant_id")), project.getId());
        String participantName = params.get("participant_name");
        String description = " удалил из проекта участника \"" + participantName + "\"";
        saveHistory(description, project);
        fillParticipantsAll();
    }
    
    // добавить нового участника к проекту
    public void addNewParticipant() {
        for (Participant participantName : currentParticipantList) {
            if (participant.equals(participantName.getEmail())) {
                return;
            }
        }
        participantService.addNewParticipantToProject(project.getId(), participant);
        String description = " добавил в проект участника \"" + participant + "\"";
        saveHistory(description, project);
        currentParticipantList = participantService.findParticipantsByProject(project.getId());   
    }
    
    // Валидация
    // заполнить список всех существуюющих участников 
    // для последующего добавления к проекту
    public void fillParticipantsForValiator() {
        currentParticipantList = participantService.getAll();
    }

    public List<Participant> getCurrentParticipantList() {
        return currentParticipantList;
    }

    public void setCurrentParticipantList(ArrayList<Participant> currentParticipantList) {
        this.currentParticipantList = currentParticipantList;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
    
    

}
