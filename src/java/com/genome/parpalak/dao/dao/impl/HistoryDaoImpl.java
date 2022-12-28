package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.HistoryDao;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.HistoryDaoQuery.*;
import com.genome.parpalak.dao.dto.HistoryDto;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryDaoImpl implements HistoryDao {
    
    private static final Logger LOGGER = Logger.getLogger(HistoryDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    
    public HistoryDaoImpl() {}

    @Override
    public void save(History history) {
        if (history == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null history");
            throw new IllegalArgumentException("Can't save null history");
        }
        int affected = entityManager.updateData(MERGE_HISTORY_OBJECT, history.getDescription(), history.getProjectId(), history.getParticipantId());
        LOGGER.log(Level.INFO, "History was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(History history) {
        if (history == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null history");
            throw new IllegalArgumentException("Can't delete null history");
        }
        int affected = entityManager.deleteData(DELETE_HISTORY, history.getId());
        LOGGER.log(Level.INFO, "History with id " + history.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public History find(int id) {
        List<History> histories = entityManager.selectData(FIND_HISTORY_BY_ID, new HistoryMapper(), id);
        History history = histories.get(0);
        if (history.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null History with id {0}", id);
            return history;
        } else {
            LOGGER.log(Level.INFO, "Found null History");
            return history;
        }
    }

    @Override
    public List<History> getAll() {
        List<History> histories = entityManager.selectData(FIND_ALL_HISTORIES, new HistoryMapper());
        LOGGER.log(Level.INFO, "Found {0} history objects", histories.size());
        return histories;
    }
    
    @Override
    public List<HistoryDto> getHistoriesByProject(int projectId) {
        String[] args = new String[] {"HISTORY_ID", "DESCRIPTION", "DATETIME", "NAME"};
        ArrayList<ArrayList> listOfLists = entityManager.selectData(FIND_HISTORIES_BY_PROJECT, args, projectId);
        
        ArrayList<HistoryDto> historyDtos = new ArrayList<>();
        
        for (ArrayList list : listOfLists) {
            HistoryDto historyDto = new HistoryDto();
            historyDto.setId(Integer.valueOf(list.get(0).toString()));
            historyDto.setDescription(list.get(1).toString());
            historyDto.setDatetime((Date)list.get(2));
            historyDto.setParticipant(list.get(3).toString());
            historyDtos.add(historyDto);
        }
        LOGGER.log(Level.INFO, "Found {0} historyDto objects", historyDtos.size());
        return historyDtos;
    }
    
    
    class HistoryMapper implements RowMapper<History> {

        @Override
        public History mapRow(ResultSet rs, int rowNum) throws SQLException {
            History history = new History();
            history.setId(rs.getInt("HISTORY_ID"));
            history.setDescription(rs.getString("DESCRIPTION"));
            history.setDatetime(rs.getDate("DATETIME"));
            history.setProjectId(rs.getInt("PROJECT_ID"));
            history.setParticipantId(rs.getInt("PARTICIPANT_ID"));
            return history;
        }
        
    }
    
}
