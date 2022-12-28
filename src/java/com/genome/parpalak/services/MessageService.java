package com.genome.parpalak.services;

import com.genome.parpalak.dao.dto.MessageDto;
import com.genome.parpalak.dao.model.Message;
import java.util.List;

public interface MessageService {
    
    void save(Message message);
    void delete (Message message);
    Message find(int id);
    List<Message> getAll();
    List<MessageDto> getMessagesByProject(int projectId);
    
}
