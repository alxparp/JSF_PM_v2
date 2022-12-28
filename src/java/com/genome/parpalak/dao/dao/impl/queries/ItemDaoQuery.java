package com.genome.parpalak.dao.dao.impl.queries;

public interface ItemDaoQuery {
    
    String FIND_ALL_ITEMS = "SELECT ITEM_ID, NAME, COMPLETED, CHECK_LIST_ID FROM ITEMS";
    
    String FIND_ITEM_BY_ID = "SELECT ITEM_ID, NAME, COMPLETED, CHECK_LIST_ID FROM ITEMS WHERE ITEM_ID = ?";
    
    String DELETE_ITEM = "DELETE FROM ITEMS WHERE ITEM_ID = ?";
    
    String MERGE_ITEM_OBJECT = "INSERT INTO ITEMS(COMPLETED, NAME, CHECK_LIST_ID) "
                + "VALUES(0, ?, ?)";
    
    String DELETE_ITEM_BY_CHECKLIST = "DELETE FROM ITEMS WHERE ITEM_ID = ? AND CHECK_LIST_ID = ?";
    
    String FIND_ITEMS_BY_CHECKLIST = "SELECT ITEM_ID, NAME, COMPLETED, CHECK_LIST_ID "
            + "FROM ITEMS "
            + "WHERE CHECK_LIST_ID = ?";
    
}
