package com.genome.parpalak.dao.utils;

import com.genome.parpalak.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManager {
    
    private static final Logger LOGGER = Logger.getLogger(EntityManager.class.getName());
    
    public int updateData(String sql, Object ... args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        try {
            conn = Database.getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i+1, args[i]);
            }
            int rows = stmt.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public <T> ArrayList<T> selectData(String sql, RowMapper<T> rowMapper,  Object ... fields) {
        PreparedStatement stmt = null;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;

        ArrayList<T> ar = new ArrayList<>();

        try {
            conn = Database.getConnection();
            if(fields.length > 0) {
                stmt = conn.prepareStatement(sql);
                for (int i = 0; i < fields.length; i++) {
                    stmt.setObject(i+1, fields[i]);
                }
                rs = stmt.executeQuery();
            } else {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
            }
            while (rs.next()) {
                ar.add(rowMapper.mapRow(rs,1));
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return ar;

    }

    @Deprecated
    public ArrayList<ArrayList> selectData(String sql, String[] args,  Object ... fields) {
        PreparedStatement stmt = null;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;

        ArrayList<ArrayList> listOfList = new ArrayList<>();

        try {
            conn = Database.getConnection();
            if(fields.length > 0) {
                stmt = conn.prepareStatement(sql);
                for (int i = 0; i < fields.length; i++) {
                    stmt.setObject(i+1, fields[i]);
                }
                rs = stmt.executeQuery();
            } else {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
            }
            while (rs.next()) {
                ArrayList ar = new ArrayList();
                for (int i = 0; i < args.length; i++) {
                    Object object = rs.getObject(args[i]);
                    if(object == null)
                        ar.add("");
                    else
                        ar.add(object);
                }
                listOfList.add(ar);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return listOfList;

    }
    
    public int deleteData(String sql, Object ... fields) {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = Database.getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < fields.length; i++) {
                stmt.setObject(i+1, fields[i]);
            }
            int rows = stmt.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }

        return 0;
    }

}
