package com.genome.parpalak.dao.dao.impl.queries;

public interface BoardDaoQuery {
    
    String MERGE_BOARD_OBJECT = "INSERT INTO BOARD(NAME) "
            + "VALUE(?)";
    
    String DELETE_BOARD = "DELETE FROM BOARD WHERE BOARD_ID = ?";
    
    String FIND_BOARD_BY_ID = "SELECT BOARD_ID, NAME "
            + "FROM BOARD "
            + "WHERE BOARD_ID = ?";
    
    String FIND_ALL_BOARD = "SELECT BOARD_ID, NAME FROM BOARD";
    
}
