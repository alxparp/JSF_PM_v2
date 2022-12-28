package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.Comment;
import com.genome.parpalak.dao.dao.impl.ParticipantDaoImpl;
import com.genome.parpalak.dao.dto.CommentDto;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.CommentService;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ParticipantService;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class CommentController {

    @Inject
    private CommentService commentService;
    
    @Inject
    private HistoryService historyService;
    
    @Inject
    private ParticipantService participantService;
    
    private List<CommentDto> currentCommentsList;
    private String description;
    private static final Logger LOGGER = Logger.getLogger(CommentController.class.getName());

    @PostConstruct
    public void init() {
        fillCommentsList();
        LOGGER.log(Level.INFO, "Comment Controller has been initialized...");
    }

    public void fillCommentsList() {
        int selectedStoryId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedStoryId");
        currentCommentsList = commentService.getCommentsByTask(selectedStoryId);
    }
    
    private void saveHistory(String historyDescription, User user) {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        History history = new History(historyDescription, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history);
    }

    public void addComment() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int selectedStoryId = Integer.valueOf(params.get("task_id"));
        String storyName = params.get("task_name");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        Comment comment = new Comment(description, new ParticipantDaoImpl().findParticipantByUsername(user.getUsername()).getId(), selectedStoryId);
        commentService.save(comment);
        String historyDescription = " добавил новую итерацию \"" + storyName + "\"";
        saveHistory(historyDescription, user);
        fillCommentsList();
        description = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CommentDto> getCurrentCommentsList() {
        return currentCommentsList;
    }

    public void setCurrentCommentsList(List<CommentDto> currentCommentsList) {
        this.currentCommentsList = currentCommentsList;
    }
    
    

}
