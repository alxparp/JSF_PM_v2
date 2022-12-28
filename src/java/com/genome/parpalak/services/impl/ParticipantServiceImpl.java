package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.ParticipantDao;
import com.genome.parpalak.dao.model.Participant;
import com.genome.parpalak.services.ParticipantService;
import java.util.List;
import javax.inject.Inject;

public class ParticipantServiceImpl implements ParticipantService {

    @Inject
    private ParticipantDao participantDao;
    
    ParticipantServiceImpl() {}
    
    @Override
    public void save(Participant participant) {
        participantDao.save(participant);
    }

    @Override
    public void delete(Participant participant) {
        participantDao.delete(participant);
    }

    @Override
    public Participant find(int id) {
       return participantDao.find(id);
    }

    @Override
    public List<Participant> getAll() {
       return participantDao.getAll();
    }

    @Override
    public void addNewParticipantToProject(int projectId, String email) {
        participantDao.addNewParticipantToProject(projectId, email);
    }

    @Override
    public void deleteParticipantFromProject(int participantId, int projectId) {
        participantDao.deleteParticipantFromProject(participantId, projectId);
    }

    @Override
    public Participant findParticipantByUsername(String username) {
        return participantDao.findParticipantByUsername(username);
    }

    @Override
    public List<Participant> findParticipantsByProject(int projectId) {
        return participantDao.findParticipantsByProject(projectId);
    }

    @Override
    public void deleteParticipantFromStory(String taskId) {
        participantDao.deleteParticipantFromStory(taskId);
    }

    @Override
    public void setParticipantToStory(int participantId, int taskId) {
        participantDao.setParticipantToStory(participantId, taskId);
    }

    @Override
    public List<String> getAllUsername() {
        return participantDao.getAllUsername();
    }

    @Override
    public List<String> getAllEmail() {
        return participantDao.getAllEmail();
    }
    
    
    
}
