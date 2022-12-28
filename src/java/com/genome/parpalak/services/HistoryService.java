package com.genome.parpalak.services;

import com.genome.parpalak.dao.dto.HistoryDto;
import com.genome.parpalak.dao.model.History;
import java.util.List;

public interface HistoryService {
    
    void save(History history);
    void delete (History history);
    History find(int id);
    List<History> getAll();
    List<HistoryDto> getHistoriesByProject(int projectId);
    
}
