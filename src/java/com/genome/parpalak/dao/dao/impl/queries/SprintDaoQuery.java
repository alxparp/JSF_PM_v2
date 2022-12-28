package com.genome.parpalak.dao.dao.impl.queries;

public interface SprintDaoQuery {
    
    String MERGE_SPRINT_OBJECT = "INSERT INTO SPRINT(NAME, START, END, PROJECT_ID, COMPLETED) "
                + "VALUES(?, ?, ?, ?, 0)";
    
    String RELEASE_TASKS_FROM_SPRINT = "UPDATE TASK SET SPRINT_ID = NULL WHERE TABLE_ID = 1 OR TABLE_ID = 2";
    
    String DELETE_SPRINT = "DELETE FROM SPRINT WHERE SPRINT_ID = ?";
    
    String FIND_ALL_SPRINTS = "SELECT SPRINT_ID, NAME, START, END, PROJECT_ID, (END <= NOW()) AS LAST_SPRINT_IN_WORK, COMPLETED "
                + "FROM SPRINT "
                + "ORDER BY SPRINT_ID DESC";
    
    String FIND_SPRINT_BY_ID = "SELECT SPRINT_ID, NAME, START, END, PROJECT_ID, (END <= NOW()) AS LAST_SPRINT_IN_WORK, COMPLETED "
                + "FROM SPRINT "
                + "WHERE SPRINT_ID = ?";
    
    String FIND_SPRINTS_BY_PROJECT = "SELECT SPRINT_ID, NAME, START, END, PROJECT_ID, (END <= NOW()) AS LAST_SPRINT_IN_WORK, COMPLETED "
                + "FROM SPRINT "
                + "WHERE PROJECT_ID + ? "
                + "ORDER BY SPRINT_ID DESC";
    
    String COUNT_OF_SPRINTS = "SELECT COUNT(SPRINT_ID) AS COUNT_SPRINT "
                + "FROM TASK "
                + "WHERE BOARD_ID = 1 OR BOARD_ID = 2 "
                + "AND SPRINT_ID = ?";
    
    String IS_SPRINT_COMPLETED = "SELECT COMPLETED "
                + "FROM SPRINT "
                + "WHERE SPRINT_ID = ?";
    
    String MAX_SPRINT_ID = "SELECT MAX(SPRINT_ID) AS MAX_SPRINT_ID FROM SPRINT";
    
    String FINISH_SPRINT = "UPDATE SPRINT SET COMPLETED = 1 WHERE SPRINT_ID = ?";
}
