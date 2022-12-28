package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.ProjectService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class ProjectController implements Serializable {

    @Inject
    private ProjectService projectService;
    
    private List<Project> currentProjectList;
    private boolean editable; 
    private boolean role = true; 
    private Project project;
    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    @PostConstruct
    public void init() {
        project = new Project();
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        role = req.isUserInRole("ADMIN");
        currentProjectList = fillProjectsAllWithRole(role);
        LOGGER.log(Level.INFO, "Project Controller has been initialized...");
    }

    public List<Project> fillProjectsAllWithRole(boolean role) {
        if (role) {
            this.role = true;
            return projectService.getAll();
        } else {
            this.role = false;
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            return projectService.findProjectsByUsername(user.getUsername());
        }
    }

    public void fillProjectsAll() {
        currentProjectList = fillProjectsAllWithRole(role);
    }

    public List<Project> getProjectList() {
        fillProjectsAllWithRole(role);
        return currentProjectList;
    }

    public void addNewProject() {
        projectService.save(project);
        project = new Project();
        fillProjectsAll();
    }

    public void deleteProject(Project project) {
        projectService.delete(project);
        fillProjectsAll();
    }

    public String fromProjectToMain(Project project) { 
        setEditable(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("project", project);
        return "main";
    }

    public List<Project> getCurrentProjectList() {
        return currentProjectList;
    }

    public void setCurrentProjectList(ArrayList<Project> currentProjectList) {
        this.currentProjectList = currentProjectList;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    

}
