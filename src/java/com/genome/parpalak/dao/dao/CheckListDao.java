package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.CheckList;
import java.util.List;

public interface CheckListDao {
    
    void save(CheckList checkList);
    void delete (CheckList checkList);
    CheckList find(int id);
    List<CheckList> getAll();
    List<CheckList> getCheckListsByTask(int taskId);
    
}
