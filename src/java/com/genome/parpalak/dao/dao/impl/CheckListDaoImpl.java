package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.CheckListDao;
import com.genome.parpalak.dao.dao.ItemDao;
import com.genome.parpalak.dao.model.CheckList;
import java.util.ArrayList;
import java.util.List;
import static com.genome.parpalak.dao.dao.impl.queries.CheckListDaoQuery.*;
import com.genome.parpalak.dao.utils.EntityManager;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

public class CheckListDaoImpl implements CheckListDao {
    
    private static final Logger LOGGER = Logger.getLogger(CheckListDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    @Inject
    private ItemDao itemDao;
    
    public CheckListDaoImpl() {}

    @Override
    public void save(CheckList checkList) {
        if (checkList == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null checkList");
            throw new IllegalArgumentException("Can't save null checkList");
        }
        Object[] fields = new Object[] {checkList.getId(), checkList.getName(), checkList.getTaskId()};
        int affected = entityManager.updateData(MERGE_CHECKLIST_OBJECT, fields);
        LOGGER.log(Level.INFO, "CheckList was saved, affected {0} rows", affected);
        
    }
    
    @Override
    public void delete(CheckList checkList) {
        if (checkList == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null checkList");
            throw new IllegalArgumentException("Can't delete null checkList");
        }
        int affected = entityManager.deleteData(DELETE_CHECKLIST, checkList.getId());
        LOGGER.log(Level.INFO, "CheckList with id " + checkList.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public CheckList find(int id) {
        List<CheckList> checkLists = entityManager.selectData(FIND_CHECKLIST_BY_ID, new CheckListMapper(), id);
        CheckList checkList = checkLists.get(0);
        if (checkList.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null CheckList with id {0}", id);
            return checkList;
        } else {
            LOGGER.log(Level.INFO, "Found null CheckList");
            return checkList;
        }
    }

    @Override
    public List<CheckList> getAll() {
        List<CheckList> checkLists = entityManager.selectData(FIND_ALL_CHECKLISTS, new CheckListMapper());
        LOGGER.log(Level.INFO, "Found {0} checkList objects", checkLists.size());
        return checkLists;
    }
    
    @Override
    public List<CheckList> getCheckListsByTask(int taskId) {
        ArrayList<CheckList> checkLists = entityManager.selectData(FIND_CHECKLISTS_BY_TASK, new CheckListMapper(), taskId);
        for (CheckList checkList : checkLists) {
            checkList.setItems(itemDao.findItemsByCheckList(checkList));
        }
        LOGGER.log(Level.INFO, "Found {0} checkList objects", checkLists.size());
        return checkLists;
    }
    
    class CheckListMapper implements RowMapper<CheckList> {

        @Override
        public CheckList mapRow(ResultSet rs, int rowNum) throws SQLException {
            CheckList checkList = new CheckList();
            checkList.setId(rs.getInt("CHECK_LIST_ID"));
            checkList.setName(rs.getString("NAME"));
            checkList.setTaskId(rs.getInt("TASK_ID"));
            return checkList;
        }
        
    }
    
}
