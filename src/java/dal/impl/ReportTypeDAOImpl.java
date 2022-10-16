/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 16, 2022         1.0           LanBTHHE160676     First Implement
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal.impl;

import dal.DBContext;
import dal.IReportTypeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ReportType;

/**				
 * The class contains method find update, delete, insert staff information from				
 * Staff table in database. In the update or insert method, all data will be normalized (trim space) before update/insert into database				
 * The method wil throw an object  of <code>java.lang.Exception</code> class if there is any error occurring when finding, inserting, or updating data				
 * <p>Bugs: Haven't found yet				
 *				
 * @author LanBTHHE160676				
 */
public class ReportTypeDAOImpl extends DBContext implements  IReportTypeDAO{

    @Override
    public List<ReportType> getAllReportTypes() throws Exception {
        List<ReportType> list = new ArrayList<>();
        String sql = "SELECT * FROM ReportType";

        PreparedStatement statement = null;
        ResultSet result = null;
        Connection connection = getConnection();

        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                ReportType reportType = new ReportType();
                reportType.setId(result.getInt("rtype_id"));
                reportType.setType(result.getString("report_type"));
                list.add(reportType);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            closeConnection(connection, statement, null);
        }

        return list;
    }

}
