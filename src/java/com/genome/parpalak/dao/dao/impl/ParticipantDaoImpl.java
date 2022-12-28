package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.ParticipantDao;
import com.genome.parpalak.dao.model.Participant;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.ParticipantDaoQuery.*;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantDaoImpl implements ParticipantDao {

    private static final Logger LOGGER = Logger.getLogger(MessageDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();

    public ParticipantDaoImpl() {}
    
    @Override
    public List<String> getAllUsername() {
        ArrayList<String> usersList = new ArrayList<>();
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_ALL_USERNAMES, new String[]{"username"});
        for (ArrayList list : listOfLists) {
            usersList.add(list.get(0).toString());
        }

        return usersList;
    }
    
    @Override
    public List<String> getAllEmail() {
        ArrayList<String> emailsList = new ArrayList<>();
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_ALL_EMAILES, new String[]{"email"});
        for (ArrayList list : listOfLists) {
            emailsList.add(list.get(0).toString());
        }

        return emailsList;
    }

    // добавить участника к задаче - перенести в таск
    @Override
    public void setParticipantToStory(int participantId, int taskId) {
        int affected = entityManager.updateData(MERGE_PARTICIPANT_TO_STORY, participantId, taskId);
        LOGGER.log(Level.INFO, "Participant with id " + participantId + " was added to task with id " + taskId + ", affected {0} rows", affected);
    }
    
    // удалить участника из задачи - перенести в таск
    @Override
    public void deleteParticipantFromStory(String taskId) {
        int affected = entityManager.updateData(DELETE_PARTICIPANT_FROM_STORY, taskId);
        LOGGER.log(Level.INFO, "Participant was deleted from task with id " + taskId + ", affected {0} rows", affected);
    }

    @Override
    public void save(Participant participant) {
        if (participant == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null participant");
            throw new IllegalArgumentException("Can't save null participant");
        }
        Object[] args = new Object[] {participant.getName(), participant.getUsername(), participant.getEmail(), participant.getPassword()};
        int affected = entityManager.updateData(MERGE_PARTICIPANT_OBJECT, args);
        LOGGER.log(Level.INFO, "Participant was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Participant participant) {
        if (participant == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null participant");
            throw new IllegalArgumentException("Can't delete null participant");
        }
        int affected = entityManager.deleteData(DELETE_PARTICIPANT, participant.getId());
        LOGGER.log(Level.INFO, "Participant with id " + participant.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Participant find(int id) {
        List<Participant> participants = entityManager.selectData(FIND_PARTICIPANT_BY_ID, new ParticipantMapper(), id);
        Participant participant = participants.get(0);
        if (participant.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Participant with id {0}", id);
            return participant;
        } else {
            LOGGER.log(Level.INFO, "Found null Participant");
            return participant;
        }
    }

    @Override
    public List<Participant> getAll() {
        List<Participant> participants = entityManager.selectData(FIND_ALL_PARTICIPANTS, new ParticipantMapper());
        LOGGER.log(Level.INFO, "Found {0} participant objects", participants.size());
        return participants;
    }
    
    
    @Override
    public void addNewParticipantToProject(int projectId, String email) {
        Object[] args = new Object[] {projectId, email};
        int affected = entityManager.updateData(MERGE_NEW_PARTICIPANT_TO_PROJECT, args);
        LOGGER.log(Level.INFO, "Participant with email " + email + " was added to project with id " + projectId + ", affected {0} rows", affected);
    }
    
    @Override
    public void deleteParticipantFromProject(int participantId, int projectId) {
        int affected = entityManager.deleteData(DELETE_PARTICIPANT_FROM_PROJECT, participantId, projectId);
        LOGGER.log(Level.INFO, "Participant with id " + participantId + " was deleted from project with id " + projectId + ", affected {0} rows", affected);
    }
    
    @Override
    public Participant findParticipantByUsername(String username) {
        List<Participant> participants = entityManager.selectData(FIND_PARTICIPANT_BY_USERNAME, new ParticipantMapper(), username);
        Participant participant = participants.get(0);
        if (participant.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Participant by username {0}", username);
            return participant;
        } else {
            LOGGER.log(Level.INFO, "Not found Participant");
            return participant;
        }
    }
    
    @Override
    public List<Participant> findParticipantsByProject(int projectId) {
        List<Participant> participants = entityManager.selectData(FIND_PARTICIPANTS_BY_PROJECT, new ParticipantMapper(), projectId);
        LOGGER.log(Level.INFO, "Found {0} participant objects", participants.size());
        return participants;
    }
    
    class ParticipantMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Participant participant = new Participant();
            participant.setId(rs.getInt("PARTICIPANT_ID"));
            participant.setName(rs.getString("NAME"));
            participant.setEmail(rs.getString("EMAIL"));
            participant.setUsername(rs.getString("USERNAME"));
            participant.setPassword(rs.getString("PASSWORD"));
            return participant;
        }
        
    }

}
