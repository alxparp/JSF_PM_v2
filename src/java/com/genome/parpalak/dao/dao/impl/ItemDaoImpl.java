package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.ItemDao;
import com.genome.parpalak.dao.model.Item;
import com.genome.parpalak.dao.utils.EntityManager;
import java.util.List;
import java.util.logging.Logger;
import static com.genome.parpalak.dao.dao.impl.queries.ItemDaoQuery.*;
import com.genome.parpalak.dao.model.CheckList;
import com.genome.parpalak.dao.utils.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;


public class ItemDaoImpl implements ItemDao {
    
    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    
    public ItemDaoImpl() {}
    
    @Override
    public void deleteItemByCheckList(Item item) {
        if (item == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null item");
            throw new IllegalArgumentException("Can't delete null item");
        }
        int affected = entityManager.deleteData(DELETE_ITEM_BY_CHECKLIST, item.getId(), item.getCheckListId());
        LOGGER.log(Level.INFO, "Item with id " + item.getId() + " was deleted, affected {0} rows", affected);
    }
    
    @Override
    public List<Item> findItemsByCheckList(CheckList checkList) {
        List<Item> items = entityManager.selectData(FIND_ITEMS_BY_CHECKLIST, new ItemMapper(), checkList.getId());
        LOGGER.log(Level.INFO, "Found {0} item objects", items.size());
        return items;
    }

    @Override
    public void save(Item item) {
        if (item == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null item");
            throw new IllegalArgumentException("Can't save null item");
        }
        int affected = entityManager.updateData(MERGE_ITEM_OBJECT, item.getName(), item.getCheckListId());
        LOGGER.log(Level.INFO, "Item was saved, affected {0} rows", affected);
    }

    @Override
    public void delete(Item item) {
        if (item == null) {
            LOGGER.log(Level.SEVERE, "Attempt to delete null item");
            throw new IllegalArgumentException("Can't delete null item");
        }
        int affected = entityManager.deleteData(DELETE_ITEM, item.getId());
        LOGGER.log(Level.INFO, "Item with id " + item.getId() + " was deleted, affected {0} rows", affected);
    }

    @Override
    public Item find(int id) {
        List<Item> items = entityManager.selectData(FIND_ITEM_BY_ID, new ItemMapper(), id);
        Item item = items.get(0);
        if (item.getId() != null) {
            LOGGER.log(Level.INFO, "Found not null Item with id {0}", id);
            return item;
        } else {
            LOGGER.log(Level.INFO, "Found null Item");
            return item;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = entityManager.selectData(FIND_ALL_ITEMS, new ItemMapper());
        LOGGER.log(Level.INFO, "Found {0} item objects", items.size());
        return items;
    }
    
    class ItemMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setId(rs.getInt("ITEM_ID"));
            item.setName(rs.getString("NAME"));
            item.setCompleted(rs.getInt("COMPLETED"));
            item.setCheckListId(rs.getInt("CHECK_LIST_ID"));
            return item;
        }
        
    }
    
}
