package com.genome.parpalak.dao.dao.impl.queries;

public interface CheckListDaoQuery {
    
    String MERGE_CHECKLIST_OBJECT = "INSERT INTO CHECK_LIST(CHECK_LIST_ID, NAME, TASK_ID) "
                + "VALUES(?, ?, ?)";
    
    String FIND_CHECKLIST_BY_ID = "SELECT CHECK_LIST_ID, NAME, TASK_ID FROM CHECK_LIST WHERE CHECK_LIST_ID = ?";
    
    String DELETE_CHECKLIST = "DELETE FROM CHECK_LIST WHERE CHECK_LIST_ID = ?";
    
    String FIND_ALL_CHECKLISTS = "SELECT CHECK_LIST_ID, NAME, TASK_ID FROM CHECK_LIST";
    
    String FIND_CHECKLISTS_BY_TASK = "SELECT CHECK_LIST_ID, NAME, TASK_ID "
            + "FROM CHECK_LIST "
            + "WHERE TASK_ID = ?";
    
}
