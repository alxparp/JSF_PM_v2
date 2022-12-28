package com.genome.parpalak.dao.dao.impl;

import com.genome.parpalak.dao.dao.UserDao;
import static com.genome.parpalak.dao.dao.impl.queries.UserDaoQuery.MERGE_PARTICIPANTS_GROUPS_OBJECT;
import static com.genome.parpalak.dao.dao.impl.queries.UserDaoQuery.MERGE_USER_OBJECT;
import com.genome.parpalak.dao.model.User;
import com.genome.parpalak.dao.utils.EntityManager;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDaoImpl implements UserDao {
    
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());
    private final EntityManager entityManager = new EntityManager();
    
    @Override
    public void registerUser(User user) {
        if (user == null) {
            LOGGER.log(Level.SEVERE, "Attempt to save null user");
            throw new IllegalArgumentException("Can't save null user");
        }
        Object[] args = new Object[] {user.getName(), user.getUsername(), transformToSHA256(user.getPassword()), user.getEmail()};
        int affected = entityManager.updateData(MERGE_USER_OBJECT, args);
        LOGGER.log(Level.INFO, "User was saved, affected {0} rows", affected);
        
        affected = entityManager.updateData(MERGE_PARTICIPANTS_GROUPS_OBJECT, user.getUsername());
        LOGGER.log(Level.INFO, "PARTICIPANTS_GROUPS was saved, affected {0} rows", affected);
    }
    
    // кодировать пароль в sha256
    private String transformToSHA256(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(string.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();

            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return string;
    }
    
}
