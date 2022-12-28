package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.UserService;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@RequestScoped
public class UserController implements Serializable {
    
    @Inject
    private UserService userService;
    
    private User user;
    
    @PostConstruct
    public void init() {
        user = new User();
    }
    
    public String registration() {
        userService.registerUser(user);        
        return "index";
    }
    
    private String goToProjects() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", user);
        return "projects";
    }
    
    public String login() {
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(user.getUsername(), user.getPassword());
            return goToProjects();
        } catch (ServletException ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("com.genome.parpalak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(bundle.getString("login_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("registr_form", message);
        }
        return "index";
    }
    
    public String logout() {
        String result = "/index.xhtml?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
