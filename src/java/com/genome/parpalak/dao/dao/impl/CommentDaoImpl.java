package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.CommentDao;
import com.genome.parpalak.dao.model.Comment;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.CommentDaoQuery.*;
import com.genome.parpalak.dao.dto.CommentDto;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;


public class CommentDaoImpl implements CommentDao {
    
    private static final Logger LOGGER = Logger.getLogger(CommentDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    
    public CommentDaoImpl() {}

    @Override
    public void save(Comment comment) {
        if (comment == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null comment");
            throw new IllegalArgumentException("Can't save null comment");
        }
        int affected = entityManager.updateData(MERGE_COMMENT_OBJECT,comment.getDescription(), comment.getTaskId(), comment.getParticipantId());
        LOGGER.log(Level.INFO, "Comment was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Comment comment) {
        if (comment == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null comment");
            throw new IllegalArgumentException("Can't delete null comment");
        }
        int affected = entityManager.deleteData(DELETE_COMMENT, comment.getId());
        LOGGER.log(Level.INFO, "Comment with id " + comment.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Comment find(int id) {
        List<Comment> comments = entityManager.selectData(FIND_COMMENT_BY_ID, new CommentMapper(), id);
        Comment comment = comments.get(0);
        if (comment.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Comment with id {0}", id);
            return comment;
        } else {
            LOGGER.log(Level.INFO, "Found null Comment");
            return comment;
        }
        
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = entityManager.selectData(FIND_COMMENT_BY_ID, new CommentMapper());
        LOGGER.log(Level.INFO, "Found {0} comment objects", comments.size());
        return comments;
    }
    
    @Override
    public List<CommentDto> getCommentsByTask(int taskId) {
        String[] args = new String[] {"COMMENT_ID", "DESCRIPTION", "NAME"};
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_COMMENTS_BY_TASK, args, taskId);
        
        ArrayList<CommentDto> commentDtos = new ArrayList<>();
        
        for (ArrayList list : listOfLists) {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(Integer.valueOf(list.get(0).toString()));
            commentDto.setDescription(list.get(1).toString());
            commentDto.setParticipant(list.get(2).toString());
            commentDtos.add(commentDto);
        }
        LOGGER.log(Level.INFO, "Found {0} commentDto objects", commentDtos.size());
        return commentDtos;
    }
    
    class CommentMapper implements RowMapper<Comment> {
        
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setId(rs.getInt("COMMENT_ID"));
            comment.setDescription(rs.getString("DESCRIPTION"));
            comment.setTaskId(rs.getInt("TASK_ID"));
            comment.setParticipantId(rs.getInt("PARTICIPANT_ID"));
            return comment;
        }
        
    }
    
}
