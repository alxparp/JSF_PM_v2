package com.genome.parpalak.dao.dao;

import com.genome.parpalak.dao.model.CheckList;
import com.genome.parpalak.dao.model.Item;
import java.util.List;

public interface ItemDao {
    
    void save(Item item);
    void delete (Item item);
    Item find(int id);
    List<Item> getAll();
    List<Item> findItemsByCheckList(CheckList checkList);
    void deleteItemByCheckList(Item item);
    
}
