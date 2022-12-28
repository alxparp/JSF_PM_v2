package com.genome.parpalak.dao.dao.impl.queries;

public interface TaskDaoQuery {
    
    String MERGE_TASK_OBJECT = "INSERT INTO TASK(NAME, BOARD_ID, PROJECT_ID) "
                + "VALUE(?, 1, ?)";
    
    String DELETE_TASK = "DELETE FROM TASK WHERE TASK_ID = ?";
    
    String FIND_TASK_BY_ID = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
            + "FROM TASK "
            + "WHERE TASK_ID = ?";
    
    String FIND_ALL_TASKS = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
            + "FROM TASK";
    
    String UPDATE_TASK_TERM = "UPDATE TASK SET DATETIME = ? WHERE TASK_ID = ?";
    
    String UPDATE_DESCRIPTION = "UPDATE TASK SET DESCRIPTION = ? "
                + "WHERE TASK_ID = ?";
    
    String UPDATE_TASK_WITH_SPRINT = "UPDATE TASK SET SPRINT_ID = ? "
                    + "WHERE TASK_ID = ?";
    
    String MOVE_TASK_THROUGH_BOARDS = "UPDATE TASK SET BOARD_ID = ? WHERE TASK_ID = ?";
    
    String FIND_TASKS_BY_PROJECT = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
                + "FROM TASK "
                + "WHERE PROJECT_ID = ? "
                + "AND SPRINT_ID IS NULL "
                + "ORDER BY NAME DESC";
    
    String FIND_TASKS_BY_SEARCH = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
                + "FROM TASK "
                + "WHERE PROJECT_ID = ? "
                + "AND LOWER(NAME) LIKE ? ORDER BY NAME ";
    
    String FIND_STORY_BY_ID = "SELECT T.TASK_ID, T.NAME, T.DATETIME, T.DESCRIPTION, B.NAME AS BOARD, NULL AS PARTICIPANT_NAME "
                + "FROM TASK T, BOARD B "
                + "WHERE T.BOARD_ID = B.BOARD_ID "
                + "AND T.PARTICIPANT_ID IS NULL "
                + "AND T.TASK_ID = ? "
                + "UNION "
                + "SELECT T.TASK_ID, T.NAME, T.DATETIME, T.DESCRIPTION, B.NAME AS BOARD, P.NAME AS PARTICIPANT_NAME "
                + "FROM TASK T, BOARD B, PARTICIPANT P "
                + "WHERE T.BOARD_ID = B.BOARD_ID "
                + "AND P.PARTICIPANT_ID = T.PARTICIPANT_ID "
                + "AND T.TASK_ID = ?";
    
    String FIND_TASKS_ALL_BY_PROJECT = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
                + "FROM TASK "
                + "WHERE PROJECT_ID = ?";
    
    String FIND_TASKS_BY_SPRINT = "SELECT TASK_ID, NAME, BOARD_ID, DATETIME, DESCRIPTION, PROJECT_ID, SPRINT_ID, PARTICIPANT_ID "
                + "FROM TASK "
                + "WHERE PROJECT_ID = ? "
                + "AND SPRINT_ID = ? "
                + "ORDER BY NAME DESC";
    
}
