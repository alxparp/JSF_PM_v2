package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.dto.HistoryDto;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.services.HistoryService;
import java.io.Serializable;
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
public class HistoryController implements Serializable {
    
    @Inject
    private HistoryService historyService;
    
    private List<HistoryDto> currentHistories;
    private Project project;
    private static final Logger LOGGER = Logger.getLogger(HistoryController.class.getName());
    
    @PostConstruct
    public void init() {
        project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        if (project != null)
            currentHistories = historyService.getHistoriesByProject(project.getId());
        LOGGER.log(Level.INFO, "History Controller has been initialized...");
    }

    public List<HistoryDto> getCurrentHistories() {
        return currentHistories;
    }

    public void setCurrentHistories(List<HistoryDto> currentHistories) {
        this.currentHistories = currentHistories;
    }
    
    
 
}
