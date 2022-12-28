package com.genome.parpalak.services;

import com.genome.parpalak.dao.model.Participant;
import java.util.List;

public interface ParticipantService {
    
    void save(Participant participant);
    void delete (Participant participant);
    Participant find(int id);
    List<Participant> getAll();
    void addNewParticipantToProject(int projectId, String email);
    void deleteParticipantFromProject(int participantId, int projectId);
    Participant findParticipantByUsername(String username);
    List<Participant> findParticipantsByProject(int projectId);
    void deleteParticipantFromStory(String taskId);
    void setParticipantToStory(int participantId, int taskId);
    List<String> getAllUsername();
    List<String> getAllEmail();
    
}
