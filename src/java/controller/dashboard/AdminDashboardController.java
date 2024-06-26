/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 22, 2022       1.0           DucPTMHE160517     First Implement
 */
package controller.dashboard;

import controller.auth.LoginController;
import dao.IContractDAO;
import dao.IPropertyDAO;
import dao.IReportDAO;
import dao.IUserDAO;
import dao.impl.ContractDAOImpl;
import dao.impl.PropertyDAOImpl;
import dao.impl.ReportDAOImpl;
import dao.impl.UserDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Report;
import model.User;

/**
 * The Servlet responsible for handling Admin Dashboard function
 * /dashboard/admin is the URL The method will throw an object of
 * <code>java.lang.Exception</code> class if there is any error occurring when
 * finding, inserting, or updating data
 * <p>
 * Bugs: Haven't found yet
 *
 * @author DucPTMHE160517
 */
public class AdminDashboardController extends HttpServlet {

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
        User user = (User) session.getAttribute("user");
        
        //if user is not login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + request.getServletPath()); //redirect to login page
            return;
        }
        try {
            //check if session user different from null and role equals to 1 (admin)
            if (user.getRole() == 1) {

                IUserDAO userDAO = new UserDAOImpl();
                IReportDAO reportDAO = new ReportDAOImpl();
                IContractDAO contractDAO = new ContractDAOImpl();
                IPropertyDAO propertyDAO = new PropertyDAOImpl();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //give format for date

                int numberOfUser = userDAO.getAllUserSystem().size(); //get number of system user
                int numberOfProperty = propertyDAO.getAllProperties().size(); //get number of system property

                List<Report> reportList = reportDAO.getAllReports(); //get list of report

                int numberOfReport = reportList.size(); //get number of report in the list of report

                int numberOfContract = contractDAO.getAllContract().size(); //get number of contract in the system

                Calendar today = Calendar.getInstance(); //get current date

                Calendar firstDayOfMonth = Calendar.getInstance(); //set first day of month equals to current date
                firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1); //set first day of month equals to 1st day of current month
                Map<Date, Double> mapIncome = contractDAO.getIncomeInRangeAdmin(
                        Date.valueOf(sdf.format(firstDayOfMonth.getTime())),
                        Date.valueOf(sdf.format(today.getTime()))); //get income per day (date)
                Set<Date> setIncome = mapIncome.keySet(); //get set of date

                double totalIncome = 0; //init income

                //loop each key of set request daily
                for (Date key : setIncome) {
                    totalIncome += ((mapIncome.get(key) * 5 / 100) / 1000000); //add income (divided by 1M) to total income
                }

                request.setAttribute("page", "Dashboard"); //set page name
                request.setAttribute("mapIncome", mapIncome); //set attribute for map of income
                request.setAttribute("setIncome", setIncome); //set attribute for set of income
                request.setAttribute("totalIncome", totalIncome); //set total income
                request.setAttribute("numberOfContract", numberOfContract); //set number of contract
                request.setAttribute("reportList", reportList); //set list of report
                request.setAttribute("numberOfReport", numberOfReport); //set number of report
                request.setAttribute("numberOfUser", numberOfUser); //set number of user
                request.setAttribute("numberOfProperty", numberOfProperty); //set number of property
                request.getRequestDispatcher("../views/dashboard/admin/dashboard-admin.jsp").forward(request, response); //forward to frontend page
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", ex.getMessage()); //set attribute for message
            request.getRequestDispatcher("../views/error.jsp").forward(request, response); //forward to error page
        }

    }

}
