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
public interface UserDaoQuery {
    
    String MERGE_USER_OBJECT = "INSERT INTO PARTICIPANT(NAME, USERNAME, PASSWORD, EMAIL) "
                + "VALUES(?, ?, ?, ?)";
    
    String MERGE_PARTICIPANTS_GROUPS_OBJECT = "INSERT INTO PARTICIPANTS_GROUPS(GROUPID, USERNAME) "
            + "VALUES('user', ?)";
    
}
