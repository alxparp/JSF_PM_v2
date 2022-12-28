package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.BoardDao;
import com.genome.parpalak.dao.model.Board;
import com.genome.parpalak.services.BoardService;
import java.util.List;
import javax.inject.Inject;

public class BoardServiceImpl implements BoardService {

    @Inject
    private BoardDao boardDao;
    
    BoardServiceImpl() {}
    
    @Override
    public void save(Board board) {
        boardDao.save(board);
    }

    @Override
    public void delete(Board board) {
        boardDao.delete(board);
    }

    @Override
    public Board find(int id) {
        return boardDao.find(id);
    }

    @Override
    public List<Board> getAll() {
        return boardDao.getAll();
    }
    
}
