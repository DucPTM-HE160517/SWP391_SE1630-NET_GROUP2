/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 20, 2022         1.0           LanBTHHE160676     First Implement
 */
package dao.impl;

import dao.DBContext;
import dao.IContractDAO;
import dao.IPropertyDAO;
import dao.IUserDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Contract;
import model.ContractStatus;
import model.User;

/**
 * The class contains method find update, delete, insert user information from
 * DB The method will throw an object of <code>java.lang.Exception</code> class
 * if there is any error occurring when finding, inserting, or updating data
 * <p>
 * Bugs: Haven't found yet
 *
 * @author LanBTHHE160676
 */
public class ContractDAOImpl extends DBContext implements IContractDAO {

    /**
     *
     * @param propertyId
     * @param userId
     * @param beginDate
     * @throws Exception
     */
    @Override
    public void insertContract(int propertyId, int userId, Date beginDate) throws Exception {
        String sql = "INSERT INTO Contract (property_id ,user_id ,begin_date, cstatus_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = getConnection();
        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, propertyId);
            statement.setInt(2, userId);
            statement.setDate(3, beginDate);
            statement.setInt(4, 1);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            close(connection, statement, null);
        }
    }

    /**
     *
     * @param hostId
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @Override
    public int getNumberOfContractInRange(int hostId, Date begin, Date end) throws Exception {
        String sql = "SELECT COUNT(*) as number \n"
                + "FROM [Contract] c\n"
                + "INNER JOIN Property p\n"
                + "ON c.property_id = p.property_id\n"
                + "WHERE begin_date BETWEEN ? AND ?\n"
                + "AND p.host_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, begin.toString());
            statement.setString(2, end.toString());
            statement.setInt(3, hostId);

            result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("number");
            }

            return -1;

        } catch (Exception ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
    }

    /**
     *
     * @param hostId
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public List<User> getRenterListByDate(int hostId, Date date) throws Exception {
        List<User> renterList = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM [Contract] c\n"
                + "INNER JOIN Property p\n"
                + "ON c.property_id = p.property_id\n"
                + "WHERE p.host_id = ?\n"
                + "AND begin_date = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hostId);
            statement.setString(2, date.toString());
            result = statement.executeQuery();

            IUserDAO userDAO = new UserDAOImpl();

            while (result.next()) {
                User user = userDAO.getUserById(result.getInt("user_id"));
                renterList.add(user);
            }

            return renterList;

        } catch (Exception ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
    }

    /**
     *
     * @param hostId
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @Override
    public Map<Date, Double> getIncomeInRange(int hostId, Date begin, Date end) throws Exception {
        Map<Date, Double> map = new TreeMap<>();
        String sql = "SELECT begin_date as [date], SUM(price) as [income]\n"
                + "FROM [Contract] c\n"
                + "INNER JOIN Property p\n"
                + "ON c.property_id = p.property_id\n"
                + "WHERE begin_date BETWEEN ? AND ? \n"
                + "AND p.host_id = ? \n"
                + "GROUP BY begin_date";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, begin.toString());
            statement.setString(2, end.toString());
            statement.setInt(3, hostId);
            result = statement.executeQuery();

            while (result.next()) {
                map.put(result.getDate("date"), result.getDouble("income"));
            }

            return map;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
    }

    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    @Override
    public List<Contract> getContractByHost(int hostId) throws Exception {
        String sql = "SELECT * FROM [Contract] WHERE user_id=?";
        List<Contract> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hostId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Contract contract = new Contract();
                PropertyDAOImpl pDao = new PropertyDAOImpl();
                UserDAOImpl uDao = new UserDAOImpl();
                ContractStatusDAOImpl cDao = new ContractStatusDAOImpl();
                contract.setId(result.getInt("contract_id"));
                contract.setDate(result.getDate("begin_date"));
                contract.setProperty(pDao.getPropertyById(result.getInt("property_id")));
                contract.setUser(uDao.getUserById(result.getInt("user_id")));
                contract.setStatus(cDao.getContractStatusById(result.getInt("cstatus_id")));
                return list;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }
        return null;
    }

    @Override
    public List<Contract> getAllContract() throws Exception {
        List<Contract> list = new ArrayList<>();
        String sql = "SELECT * FROM Contract";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            IPropertyDAO propertyDAO = new PropertyDAOImpl();
            IContractDAO contractDAO = new ContractDAOImpl();
            while (result.next()) {
                Contract contract = new Contract();
                contract.setId(result.getInt("contract_id"));
                contract.setProperty(propertyDAO.getPropertyById(result.getInt("property_id")));
                list.add(contract);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }

        return list;
    }

    @Override
    public Map<Date, Double> getIncomeInRangeAdmin(Date begin, Date end) throws Exception {
        Map<Date, Double> map = new TreeMap<>();
        String sql = "SELECT begin_date as [date], SUM(price) as [income]\n"
                + "FROM [Contract] c\n"
                + "INNER JOIN Property p\n"
                + "ON c.property_id = p.property_id\n"
                + "WHERE begin_date BETWEEN ? AND ? \n"
                + "GROUP BY begin_date";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, begin.toString());
            statement.setString(2, end.toString());
            result = statement.executeQuery();

            while (result.next()) {
                map.put(result.getDate("date"), result.getDouble("income"));
            }

            return map;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
    }

    @Override
    public List<Contract> getContractByRenter(int renterId) throws Exception {
        String sql = "SELECT * "
                + "FROM [Contract] c INNER JOIN Property  p ON c.property_id=p.property_id "
                + "INNER JOIN ContractStatus cs ON c.cstatus_id = cs.cstatus_id "
                + "WHERE user_id=?";
        List<Contract> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, renterId);
            result = statement.executeQuery();
            while (result.next()) {
                Contract contract = new Contract();
                PropertyDAOImpl pDao = new PropertyDAOImpl();
                UserDAOImpl uDao = new UserDAOImpl();
                ContractStatusDAOImpl cDao = new ContractStatusDAOImpl();
                contract.setId(result.getInt("contract_id"));
                contract.setDate(result.getDate("begin_date"));
                contract.setProperty(pDao.getPropertyById(result.getInt("property_id")));
                contract.setUser(uDao.getUserById(result.getInt("user_id")));
                contract.setStatus(cDao.getContractStatusById(result.getInt("cstatus_id")));
                list.add(contract);
            }

            return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
        
        
    }
    public static void main(String[] args) {
        IContractDAO contractDAO = new ContractDAOImpl();
        try {
            System.out.println(contractDAO.getContractByRenter(1));
        } catch (Exception e) {
            
        }
    }
}
