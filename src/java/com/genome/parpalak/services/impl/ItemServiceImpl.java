package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.ItemDao;
import com.genome.parpalak.dao.dao.impl.ItemDaoImpl;
import com.genome.parpalak.dao.model.CheckList;
import com.genome.parpalak.dao.model.Item;
import com.genome.parpalak.services.ItemService;
import java.util.List;
import javax.inject.Inject;

public class ItemServiceImpl implements ItemService {

    @Inject
    private ItemDao itemDao;
    
    public ItemServiceImpl() {}
    
    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }

    @Override
    public Item find(int id) {
        return itemDao.find(id);
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public List<Item> findItemsByCheckList(CheckList checkList) {
        return itemDao.findItemsByCheckList(checkList);
    }

    @Override
    public void deleteItemByCheckList(Item item) {
        itemDao.deleteItemByCheckList(item);
    }
    
    
    
}
