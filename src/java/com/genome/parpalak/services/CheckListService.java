package com.genome.parpalak.services;

import com.genome.parpalak.dao.model.CheckList;
import java.util.List;

public interface CheckListService {
    
    void save(CheckList checkList);
    void delete (CheckList checkList);
    CheckList find(int id);
    List<CheckList> getAll();
    List<CheckList> getCheckListsByTask(int taskId);
    
}
