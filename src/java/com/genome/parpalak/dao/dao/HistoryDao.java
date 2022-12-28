package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.dto.HistoryDto;
import com.genome.parpalak.dao.model.History;
import java.util.List;

public interface HistoryDao {
    
    void save(History history);
    void delete (History history);
    History find(int id);
    List<History> getAll();
    List<HistoryDto> getHistoriesByProject(int projectId);
    
}
