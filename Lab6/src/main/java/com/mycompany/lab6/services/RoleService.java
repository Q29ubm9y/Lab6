package com.mycompany.lab6.services;

import com.mycompany.lab6.dataaccess.*;
import com.mycompany.lab6.models.*;
import java.util.List;

public class RoleService {
    private RoleDB roleDB = new RoleDB();
    
    public List<Role> getAll() throws Exception {
        List<Role> roles = this.roleDB.getAll();
        return roles;
    }
}