package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.dto.CommentDto;
import com.genome.parpalak.dao.model.Comment;
import java.util.List;

public interface CommentDao {
    
    void save(Comment comment);
    void delete (Comment comment);
    Comment find(int id);
    List<Comment> getAll();
    List<CommentDto> getCommentsByTask(int taskId);
    
}
