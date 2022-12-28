package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.HistoryDao;
import com.genome.parpalak.dao.dto.HistoryDto;
import com.genome.parpalak.dao.model.History;
import com.genome.parpalak.services.HistoryService;
import java.util.List;
import javax.inject.Inject;

public class HistoryServiceImpl implements HistoryService {

    @Inject
    private HistoryDao historyDao;
    
    HistoryServiceImpl() {}
    
    @Override
    public void save(History history) {
        historyDao.save(history);
    }

    @Override
    public void delete(History history) {
        historyDao.delete(history);
    }

    @Override
    public History find(int id) {
        return historyDao.find(id);
    }

    @Override
    public List<History> getAll() {
        return historyDao.getAll();
    }

    @Override
    public List<HistoryDto> getHistoriesByProject(int projectId) {
        return historyDao.getHistoriesByProject(projectId);
    }
    
}
