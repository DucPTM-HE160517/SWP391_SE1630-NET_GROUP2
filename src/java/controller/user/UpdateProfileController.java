/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 16, 2022       1.0           DucPTMHE160517     First Implement
 */
package controller.user;

import dao.IUserDAO;
import dao.impl.UserDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utils.ValidateUtility;

/**
 * The class contains method find update, delete, insert user The method wil
 * throw an object of <code>java.lang.Exception</code> class if there is any
 * error occurring when finding, inserting, or updating data
 * <p>
 * Bugs: Still have some issues related to search staff by address
 *
 * @author DucPTMHE160517
 */
public class UpdateProfileController extends HttpServlet {

    private ValidateUtility validate = new ValidateUtility();

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
        HttpSession session = request.getSession();
        IUserDAO userDAO = new UserDAOImpl();
        String uid_raw = request.getParameter("uid");
        User u = (User) session.getAttribute("user");
        int id = Integer.parseInt(uid_raw);
        //if user is not login
        if (u == null) {
            request.getRequestDispatcher("login").forward(request, response);
        }

        try {
            User user = userDAO.getUserById(id);

            //if user in session
            if (user != null) {
                session.setAttribute("user", user);
            }
            request.setAttribute("user", user);
            request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("message", ex);
            request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);

        }
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
        HttpSession session = request.getSession();
        String uid_raw = request.getParameter("id");
        User user = (User) session.getAttribute("user");
        int uid = Integer.parseInt(uid_raw);
        try {
            IUserDAO userDAO = new UserDAOImpl();

            String name = validate.getField(request, "name", true, 3, 20);
            String phone = validate.getFieldByType(request, "phone", "phone", true, 9, 11);
            String email = validate.getFieldByType(request, "email", "email", true, 11, 200);
            String address = validate.getField(request, "address", true, 10, 30);

            //check if this input email equals old email
            if (email.equals(userDAO.getUserById(uid).getEmail()) || userDAO.getUserByEmail(email) == null) {
                userDAO.updateUser(uid, name, phone, email, address); //update with old email
            } else {
                throw new Exception("This email is used by another user!"); //throw new exception
            }

            User newUser = userDAO.getUserById(uid);

            //Check if new user is not null
            if (newUser != null) {
                request.setAttribute("user", newUser);
            }

            request.setAttribute(
                    "user", newUser);
            request.setAttribute(
                    "message", "Update successfully!");
            request.getRequestDispatcher(
                    "views/user/profile.jsp").forward(request, response);            
        } catch (Exception ex) {
            request.setAttribute("user", user);
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("views/user/profile.jsp").forward(request, response);
        }
    }

}
