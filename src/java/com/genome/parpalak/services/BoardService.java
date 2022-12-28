package com.genome.parpalak.services;

import com.genome.parpalak.dao.model.Board;
import java.util.List;

public interface BoardService {
    
    void save(Board board);
    void delete (Board board);
    Board find(int id);
    List<Board> getAll();
    
}
