package com.mycompany.lab6.servlets;

import com.mycompany.lab6.models.*;
import com.mycompany.lab6.services.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Connor
 */
public class UserServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService();
        RoleService roleService = new RoleService();
        try {
            List<User> users = userService.getAll();
            List<Role> roles = roleService.getAll();
            request.setAttribute("roles", roles);
            request.setAttribute("users", users);
        } catch (Exception ex) {
            request.setAttribute("message", ex);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserService userService = new UserService();
        RoleService roleService = new RoleService();
        try {
            List<Role> roles = roleService.getAll();
            request.setAttribute("roles", roles);
            String em = request.getParameter("editEmail");
            String fn = request.getParameter("editFirstName");
            String ln = request.getParameter("editLastName");
            String pw = request.getParameter("editPassword");
            String role = request.getParameter("editRole");
            String act = request.getParameter("editActive");
            if (action != null && action.equals("add") && !request.getParameter("addEmail").equals("") && !request.getParameter("addFirstName").equals("")
                    && !request.getParameter("addLastName").equals("") && !request.getParameter("addPassword").equals("") && request.getParameter("addRole") != null) {
                //If all fields aren't empty, then insert them into database
                userService.insert(request.getParameter("addEmail"), request.getParameter("addActive") != null, request.getParameter("addFirstName"),
                        request.getParameter("addLastName"), request.getParameter("addPassword"), roles.get(Integer.parseInt(request.getParameter("addRole")) - 1));
            } else if (request.getParameter("edit") != null) {
                User user = userService.get(request.getParameter("edit"));
                request.setAttribute("userEdit", user);
                request.setAttribute("checked", user.isActive() ? "checked" : "");
            } else if (request.getParameter("delete") != null) {
                userService.delete(request.getParameter("delete"));
            } else if (request.getParameter("cancel") != null) {
                request.setAttribute("userEdit", null);
            } else if (request.getParameter("save") != null && !request.getParameter("editEmail").equals("") && !request.getParameter("editFirstName").equals("")
                    && !request.getParameter("editLastName").equals("") && !request.getParameter("editPassword").equals("") && request.getParameter("editRole") != null) {
                //If all fields aren't empty, then update the database
                int i = 0;
                userService.update(request.getParameter("editEmail"), request.getParameter("editActive") != null, request.getParameter("editFirstName"), 
                        request.getParameter("editLastName"), request.getParameter("editPassword"), roles.get(Integer.parseInt(request.getParameter("editRole")) - 1));
            }
            
            List<User> users = userService.getAll();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            request.setAttribute("message", ex);
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
}
