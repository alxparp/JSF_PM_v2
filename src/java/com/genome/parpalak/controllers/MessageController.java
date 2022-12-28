package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.Message;
import com.genome.parpalak.dao.dto.MessageDto;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.MessageService;
import com.genome.parpalak.services.ParticipantService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class MessageController {

    @Inject
    private MessageService messageService;
    
    @Inject
    private ParticipantService participantService;
    
    private List<MessageDto> currentMessagesList;
    private String description;
    private static final Logger LOGGER = Logger.getLogger(MessageController.class.getName());
    
    @PostConstruct
    public void init() {
        LOGGER.log(Level.INFO, "Message Controller has been initialized...");
        fillMessagesList();
    }

    public void fillMessagesList() {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        currentMessagesList = messageService.getMessagesByProject(project.getId());
    }

    public void addMessage() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        Message message = new Message(description, participantService.findParticipantByUsername(user.getUsername()).getId(), project.getId());
        messageService.save(message);
        fillMessagesList();
        description = "";
    }

    public List<MessageDto> getCurrentMessagesList() {
        return currentMessagesList;
    }

    public void setCurrentMessagesList(List<MessageDto> currentMessagesList) {
        this.currentMessagesList = currentMessagesList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
