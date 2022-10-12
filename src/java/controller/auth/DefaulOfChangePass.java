/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 * DefaulOfChangePass
 * Record of change:
 *      DATE: Oct 11, 2022            
 *      VERSION: 1.0
 *      AUTHOR: ThuongTTHE163555         
 */

package controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * [FILE DESCRIPTION HERE]
 *
 * @author ThuongTTHE163555
 */

@WebServlet(name="DefaulOfChangePass", urlPatterns={"/DefaulOfChangePassword"})
public class DefaulOfChangePass extends HttpServlet {
   
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/DefaulOfChangePassword.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    }

}