package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.dto.MessageDto;
import com.genome.parpalak.dao.model.Message;
import java.util.List;

public interface MessageDao {
    
    void save(Message message);
    void delete (Message message);
    Message find(int id);
    List<Message> getAll();
    List<MessageDto> getMessagesByProject(int projectId);
    
}
