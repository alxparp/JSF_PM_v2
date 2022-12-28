/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genome.parpalak.dao.dao.impl.queries;

/**
 *
 * @author Parpalak Alexander
 */
public interface HistoryDaoQuery {
    
    String MERGE_HISTORY_OBJECT = "INSERT INTO HISTORY(DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID) "
                + "values(?, NOW(), ?, ?)";
    
    String DELETE_HISTORY = "DELETE FROM HISTORY WHERE HISTORY_ID = ?";
    
    String FIND_HISTORY_BY_ID = "SELECT HISTORY_ID, DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID "
            + "FROM HISTORY "
            + "WHERE HISTORY_ID = ?";
    
    String FIND_ALL_HISTORIES = "SELECT HISTORY_ID, DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID "
            + "FROM HISTORY ";
    
    String FIND_HISTORIES_BY_PROJECT = "SELECT H.HISTORY_ID, H.DESCRIPTION, H.DATETIME, P.NAME "
                + "FROM HISTORY H, PARTICIPANT P "
                + "WHERE P.PARTICIPANT_ID = H.PARTICIPANT_ID "
                + "AND H.PROJECT_ID = ? "
                + "ORDER BY H.HISTORY_ID DESC";
    
}
