package com.genome.parpalak.controllers;

import com.genome.parpalak.dao.model.CheckList;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.dao.model.Item;
import com.genome.parpalak.dao.model.Project;
import com.genome.parpalak.services.CheckListService;
import com.genome.parpalak.services.HistoryService;
import com.genome.parpalak.services.ItemService;
import com.genome.parpalak.services.ParticipantService;
import java.io.Serializable;
import java.util.ArrayList;
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
public class CheckListController implements Serializable {
    
    @Inject
    private CheckListService checkListService;
    
    @Inject
    private ItemService itemService;
    
    @Inject
    private HistoryService historyService;
    
    @Inject
    private ParticipantService participantService;
    
    private List<CheckList> currentCheckLists;
    private String newItem;
    private String newCheckList;
    private static final Logger LOGGER = Logger.getLogger(CheckListController.class.getName());
    
    @PostConstruct
    public void init() {
        fillStoryWithCheckLists();
        LOGGER.log(Level.INFO, "CheckList Controller has been initialized...");
    }
    
    public void fillStoryWithCheckLists() {
        int selectedStoryId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedStoryId");   
        currentCheckLists = checkListService.getCheckListsByTask(selectedStoryId);
//        for(CheckList checkList : currentCheckLists) {
//            checkList.setItems(itemService.findItemsByCheckList(checkList));
//        }    
    }
    
    private void saveHistory(String description) {
        Project project = (Project) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("project");
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        History history = new History(description, project.getId(), participantService.findParticipantByUsername(user.getUsername()).getId());
        historyService.save(history);
    }
    
    public void addNewItem() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer checkListId = Integer.valueOf(params.get("check_list_id"));
        String name = getNewItem();
        itemService.save(new Item(name, checkListId));
        fillStoryWithCheckLists();
        newItem = "";
        String description = " добавил пункт \"" + name + "\" в чеклист \"" + currentCheckLists.get(0).getName() + "\"";
        saveHistory(description);
    }
    
    public void deleteItem() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer checkListId = Integer.valueOf(params.get("check_list_id"));
        Integer itemId = Integer.valueOf(params.get("item_id"));
        itemService.deleteItemByCheckList(new Item(itemId, checkListId));
        String description = " удалил пункт \"" + params.get("item_name") + "\" из чеклиста \"" + currentCheckLists.get(0).getName() + "\"";
        saveHistory(description);
        fillStoryWithCheckLists();
    }
    
    public void deleteCheckList() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        checkListService.delete(checkListService.find(Integer.valueOf(params.get("check_list_id"))));
        String description = " удалил чек-лист \"" + params.get("check_list_name") + "\" из задании \"" + params.get("task_name") + "\"";
        saveHistory(description);
        fillStoryWithCheckLists();
    }
    
    public void addNewCheckList() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String taskId = params.get("task_id");
        checkListService.save(new CheckList(newCheckList, Integer.valueOf(taskId)));
        String description = " добавил чек-лист \"" + newCheckList + "\" в задании \"" + params.get("task_name") + "\"";
        saveHistory(description);
        fillStoryWithCheckLists();
        newCheckList = "";
    }

    public List<CheckList> getCurrentCheckLists() {
        return currentCheckLists;
    }

    public void setCurrentCheckLists(ArrayList<CheckList> currentCheckLists) {
        this.currentCheckLists = currentCheckLists;
    }

    public String getNewItem() {
        return newItem;
    }

    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    public String getNewCheckList() {
        return newCheckList;
    }

    public void setNewCheckList(String newCheckList) {
        this.newCheckList = newCheckList;
    }
    
    
  
}
