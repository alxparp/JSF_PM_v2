package com.genome.parpalak.services;

import com.genome.parpalak.dao.dto.CommentDto;
import com.genome.parpalak.dao.model.Comment;
import java.util.List;

public interface CommentService {
    
    void save(Comment comment);
    void delete (Comment comment);
    Comment find(int id);
    List<Comment> getAll();
    List<CommentDto> getCommentsByTask(int taskId);
    
}
