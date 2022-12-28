package com.genome.parpalak.services.impl;

import com.genome.parpalak.dao.dao.UserDao;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.services.UserService;
import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;
    
    UserServiceImpl() {}
    
    @Override
    public void registerUser(User user) {
        userDao.registerUser(user);
    }
    
}
