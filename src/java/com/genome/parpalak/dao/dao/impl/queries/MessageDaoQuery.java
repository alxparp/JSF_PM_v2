package com.genome.parpalak.dao.dao.impl.queries;

public interface MessageDaoQuery {
    
    String MERGE_MESSAGE_OBJECT = "INSERT INTO MESSAGE(DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID) "
            + "VALUE(?,NOW(),?,?)";
    
    String DELETE_MESSAGE = "DELETE FROM MESSAGE WHERE MESSAGE_ID = ?";
    
    String FIND_MESSAGE_BY_ID = "SELECT MESSAGE_ID, DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID "
            + "FROM MESSAGE "
            + "WHERE MESSAGE_ID = ?";
    
    String FIND_ALL_MESSAGES = "SELECT MESSAGE_ID, DESCRIPTION, DATETIME, PROJECT_ID, PARTICIPANT_ID "
            + "FROM MESSAGE";
    
    String FIND_MESSAGES_BY_PROJECT = "SELECT M.MESSAGE_ID, M.DESCRIPTION, M.DATETIME, P.NAME "
                + "FROM MESSAGE M, PARTICIPANT P "
                + "WHERE M.PARTICIPANT_ID = P.PARTICIPANT_ID "
                + "AND M.PROJECT_ID = ? "
                + "ORDER BY M.MESSAGE_ID DESC";
    
}
