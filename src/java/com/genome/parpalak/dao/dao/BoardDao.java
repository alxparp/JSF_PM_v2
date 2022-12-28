package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.Board;
import java.util.List;

public interface BoardDao {
    
    void save(Board board);
    void delete (Board board);
    Board find(int id);
    List<Board> getAll();
    
}
