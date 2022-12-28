package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.CommentDao;
import com.genome.parpalak.dao.dto.CommentDto;
import com.genome.parpalak.dao.model.Comment;
import com.genome.parpalak.services.CommentService;
import java.util.List;
import javax.inject.Inject;

public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentDao commentDao;
    
    CommentServiceImpl() {}
    
    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public Comment find(int id) {
        return commentDao.find(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    public List<CommentDto> getCommentsByTask(int taskId) {
        return commentDao.getCommentsByTask(taskId);
    }
    
    
    
}
