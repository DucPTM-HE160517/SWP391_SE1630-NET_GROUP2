/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 4, 2022         1.0           DucPTMHE160517     First Implement
 */
package controller.dashboard;

import dao.IPropertyDAO;
import dao.IUserDAO;
import dao.impl.PropertyDAOImpl;
import dao.impl.UserDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Property;
import model.User;

/**
 * The class contains method find update, delete, insert staff information from
 *
 * The method will throw an object of <code>java.lang.Exception</code> class if
 * there is any error occurring when finding, inserting, or updating data
 * <p>
 * Bugs: Haven't found yet
 *
 * @author DucPTMHE160517
 */
public class DashboardPropertiesController extends HttpServlet {

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
        User user = (User) session.getAttribute("user");

        //if user is not login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + request.getServletPath());
            return;
        }

        try {
            if (user.getRole() == 2) { //check if role of user equal to 2
                IPropertyDAO propertyDAO = new PropertyDAOImpl();
                List<Property> listProperty = propertyDAO.getPropertyByHostId(user.getId()); //get property list of current login host

                List<Property> finalList = new ArrayList<>(); //init final list
                int numberItemPerPage = 3; //set number of property per page
                int sizeOfList = listProperty.size(); //get number of property own by current host

                int numberOfPage = sizeOfList / numberItemPerPage + (sizeOfList % numberItemPerPage == 0 ? 0 : 1); //calculate number of page
                int start, end;

                String page_raw = null;
                int currentPage = 1;

                //check if url has parameter of page
                if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                    page_raw = request.getParameter("page");
                    currentPage = Integer.parseInt(page_raw);
                }

                start = (currentPage - 1) * numberItemPerPage;
                
                //check if number of item is out of list size
                if ((currentPage * numberItemPerPage) > sizeOfList) {
                    end = sizeOfList;
                } else {
                    end = currentPage * numberItemPerPage;
                }

                //loop from start index  to end index
                for (int i = start; i < end; i++) {
                    finalList.add(listProperty.get(i)); //add related property to final list
                }
                request.setAttribute("listProperty", finalList);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("numberOfPage", numberOfPage);
                request.setAttribute("user", user);
                request.setAttribute("page", "Properties");
                request.getRequestDispatcher("../views/dashboard/property/properties.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "You don't have right to access this page!");
                request.getRequestDispatcher("../views/error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddPropertyController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", ex);
            request.getRequestDispatcher("../views/error.jsp").forward(request, response);
        }

    }

}
