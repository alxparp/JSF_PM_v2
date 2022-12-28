package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.CheckListDao;
import com.genome.parpalak.dao.model.CheckList;
import com.genome.parpalak.services.CheckListService;
import java.util.List;
import javax.inject.Inject;

public class CheckListServiceImpl implements CheckListService {

    @Inject
    private CheckListDao checkListDao;
    
    public CheckListServiceImpl() {}
    
    @Override
    public void save(CheckList checkList) {
        checkListDao.save(checkList);
    }

    @Override
    public void delete(CheckList checkList) {
        checkListDao.delete(checkList);
    }

    @Override
    public CheckList find(int id) {
        return checkListDao.find(id);
    }

    @Override
    public List<CheckList> getAll() {
        return checkListDao.getAll();
    }

    @Override
    public List<CheckList> getCheckListsByTask(int taskId) {
        return checkListDao.getCheckListsByTask(taskId);
    }
    
    
    
}
