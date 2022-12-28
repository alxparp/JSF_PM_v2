package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.BoardDao;
import com.genome.parpalak.dao.model.Board;
import java.util.List;
import static com.genome.parpalak.dao.dao.impl.queries.BoardDaoQuery.*;
import com.genome.parpalak.dao.utils.EntityManager;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoardDaoImpl implements BoardDao {
    
    private static final Logger LOGGER = Logger.getLogger(BoardDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();

    @Override
    public void save(Board board) {
        if (board == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null board");
            throw new IllegalArgumentException("Can't save null board");
        }
        Object[] args = new Object[] {board.getId()};
        int affected = entityManager.updateData(MERGE_BOARD_OBJECT, args);
        LOGGER.log(Level.INFO, "Board was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Board board) {
        if (board == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null board");
            throw new IllegalArgumentException("Can't delete null board");
        }
        int affected = entityManager.deleteData(DELETE_BOARD, board.getId());
        LOGGER.log(Level.INFO, "Board with id " + board.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Board find(int id) {
        List<Board> boards = entityManager.selectData(FIND_BOARD_BY_ID, new BoardMapper(), id);
        Board board = boards.get(0);
        if (board.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Board with id {0}", id);
            return board;
        } else {
            LOGGER.log(Level.INFO, "Found null Board");
            return board;
        }
    }

    @Override
    public List<Board> getAll() {
        List<Board> boards = entityManager.selectData(FIND_ALL_BOARD, new BoardMapper());
        LOGGER.log(Level.INFO, "Found {0} board objects", boards.size());
        return boards;
    }
    
    class BoardMapper implements RowMapper<Board> {

        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            Board board = new Board();
            board.setId(rs.getInt("BOARD_ID"));
            board.setName(rs.getString("NAME"));
            return board;
        }
        
    }
    
    
    
}
