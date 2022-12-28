package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.MessageDao;
import com.genome.parpalak.dao.dto.MessageDto;
import com.genome.parpalak.dao.model.Message;
import com.genome.parpalak.services.MessageService;
import java.util.List;
import javax.inject.Inject;

public class MessageServiceImpl implements MessageService {

    @Inject
    private MessageDao messageDao;
    
    MessageServiceImpl() {}
    
    @Override
    public void save(Message message) {
        messageDao.save(message);
    }

    @Override
    public void delete(Message message) {
        messageDao.delete(message);
    }

    @Override
    public Message find(int id) {
        return messageDao.find(id);
    }

    @Override
    public List<Message> getAll() {
        return messageDao.getAll();
    }

    @Override
    public List<MessageDto> getMessagesByProject(int projectId) {
        return messageDao.getMessagesByProject(projectId);
    }
    
    
    
}
