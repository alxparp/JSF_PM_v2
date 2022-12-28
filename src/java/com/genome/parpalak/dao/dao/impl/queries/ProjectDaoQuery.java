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
public interface ProjectDaoQuery {
    
    String MERGE_PROJECT_OBJECT = "INSERT INTO PROJECTS(NAME) "
            + "VALUE(?)";
    
    String DELETE_PROJECT = "DELETE FROM PROJECTS WHERE PROJECT_ID = ?";
    
    String FIND_PROJECT_BY_ID = "SELECT PROJECT_ID, NAME FROM PROJECTS WHERE PROJECT_ID = ?";
    
    String FIND_ALL_PROJECTS = "SELECT PROJECT_ID, NAME FROM PROJECTS ORDER BY NAME";
    
    String FIND_PROJECTS_BY_USERNAME = "SELECT PROJ.PROJECT_ID, PROJ.NAME "
                + "FROM PROJECTS PROJ, PARTICIPANT PART, PROJECT_PARTICIPANT PP "
                + "WHERE PROJ.PROJECT_ID = PP.PROJECT_ID "
                + "AND PP.PARTICIPANT_ID = PART.PARTICIPANT_ID "
                + "AND PART.USERNAME = ?";
    
}
