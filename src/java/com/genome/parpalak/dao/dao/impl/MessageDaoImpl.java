package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.MessageDao;
import com.genome.parpalak.dao.model.Message;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.MessageDaoQuery.*;
import com.genome.parpalak.dao.dto.MessageDto;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MessageDaoImpl implements MessageDao {
    
    private static final Logger LOGGER = Logger.getLogger(MessageDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    
    public MessageDaoImpl() {}

    @Override
    public void save(Message message) {
        if (message == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null message");
            throw new IllegalArgumentException("Can't save null message");
        }
        Object[] args = new Object[] {message.getDescription(), message.getProjectId(), message.getParticipantId()};
        int affected = entityManager.updateData(MERGE_MESSAGE_OBJECT, args);
        LOGGER.log(Level.INFO, "Message was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Message message) {
        if (message == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null message");
            throw new IllegalArgumentException("Can't delete null message");
        }
        int affected = entityManager.deleteData(DELETE_MESSAGE, message.getId());
        LOGGER.log(Level.INFO, "Message with id " + message.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Message find(int id) {
        List<Message> messages = entityManager.selectData(FIND_MESSAGE_BY_ID, new MessageMapper(), id);
        Message message = messages.get(0);
        if (message.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Message with id {0}", id);
            return message;
        } else {
            LOGGER.log(Level.INFO, "Found null Message");
            return message;
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = entityManager.selectData(FIND_ALL_MESSAGES, new MessageMapper());
        LOGGER.log(Level.INFO, "Found {0} history objects", messages.size());
        return messages;
    }
    
    @Override
    public List<MessageDto> getMessagesByProject(int projectId) {
        String[] args = new String[] {"MESSAGE_ID", "DESCRIPTION", "DATETIME", "NAME"};
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_MESSAGES_BY_PROJECT, args, projectId);
        
        ArrayList<MessageDto> messageDtos = new ArrayList<>();
        
        for (ArrayList list : listOfLists) {
            MessageDto messageDto = new MessageDto();
            messageDto.setId(Integer.valueOf(list.get(0).toString()));
            messageDto.setDescription(list.get(1).toString());
            if (list.get(2) != "")
                messageDto.setDatetime((Date)list.get(2));
            else
                messageDto.setDatetime(null);
            messageDto.setParticipant(list.get(3).toString());
            messageDtos.add(messageDto);
        }
        LOGGER.log(Level.INFO, "Found {0} messageDto objects", messageDtos.size());
        return messageDtos;
    }
    
    class MessageMapper implements RowMapper<Message> {

        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();
            message.setId(rs.getInt("MESSAGE_ID"));
            message.setDescription(rs.getString("DESCRIPTION"));
            message.setDatetime(rs.getDate("DATETIME"));
            message.setProjectId(rs.getInt("PROJECT_ID"));
            message.setParticipantId(rs.getInt("PARTICIPANT_ID"));
            return message;
        }
        
    }
    
}
