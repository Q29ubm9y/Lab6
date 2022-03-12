package com.mycompany.lab6.dataaccess;

import com.mycompany.lab6.models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role ORDER BY active DESC, last_name, first_name";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String email = rs.getString(1);
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(8);
                
                Role role = new Role(roleID, roleName);
                User user = new User(email, active, firstName, lastName, password, role);
                
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return users;
    }

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user JOIN role ON (role = role_id) WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(8);
                
                Role role = new Role(roleID, roleName);
                user = new User(email, active, firstName, lastName, password, role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return user;
    }

    public boolean insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email, active, first_name, last_name, password, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        boolean inserted = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.isActive() ? 1 : 0);
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getRole().getId());
            inserted = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return inserted;
    }

    public boolean update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user "
                + "SET active = ?, first_name = ?, last_name = ?, password = ?, role = ? "
                + "WHERE  email = ?";
        boolean updated = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.isActive() ? 1 : 0);
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getId());
            ps.setString(6, user.getEmail());
            updated = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return updated;
    }

    public boolean delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //String sql = "UPDATE user SET active = 0 WHERE email = ?";
        //Decided to use a hard delete since 'edit -> uncheck active -> save' functionality exists.
        String sql = "DELETE FROM user WHERE email = ?";
        
        boolean deleted = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            deleted = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return deleted;
    }

}
