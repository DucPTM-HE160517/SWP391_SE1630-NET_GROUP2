/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Oct 13, 2022         1.0           NgocCMHE161386     First Implement
 */
package dao.impl;

import dao.DBContext;
import dao.IPropertyDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Property;

/**
 * The class contains method find update, delete, insert property information
 * from DB
 *
 * The method will throw an object of <code>java.lang.Exception</code> class if
 * there is any error occurring when finding, inserting, or updating data
 * <p>
 * Bugs: Haven't found yet
 *
 * @author NgocCMHE161386
 */
public class PropertyDAOImpl extends DBContext implements IPropertyDAO {

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public List<Property> getAllProperties() throws SQLException {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM [Property]";

        PreparedStatement statement = null;
        ResultSet result = null;
        Connection connection = getConnection();

        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                PropertyImageDAOImpl pImgDao = new PropertyImageDAOImpl();
                PropertyTypeDAOImpl pTypeDao = new PropertyTypeDAOImpl();
                PropertyStatusDAO pStatusDao = new PropertyStatusDAO();
                PropertyUtilityDAOImpl pUtilityDao = new PropertyUtilityDAOImpl();
                UserDAOImpl udb = new UserDAOImpl();
                Property property = new Property();
                property.setId(result.getInt("property_id"));
                property.setName(result.getString("name"));
                property.setHost(udb.getUserById(result.getInt("host_id")));
                property.setAddress(result.getString("address"));
                property.setArea(result.getDouble("area"));
                property.setPrice(result.getDouble("price"));
                property.setTotal(result.getInt("total"));
                property.setUtilities(pUtilityDao.getUtilitiesByPID(result.getInt("property_id")));
                property.setCreatedDate(result.getDate("created_date"));
                property.setStatus(pStatusDao.getStatusByID(result.getInt("pstatus_id")));
                property.setType(pTypeDao.getTypeByID(result.getInt("type_id")));
                property.setDescription(result.getString("description"));
                property.setImages(pImgDao.getImagesByPID(result.getInt("property_id")));
                list.add(property);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }

        return list;
    }

    /**
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    @Override
    public List<Property> getPropertiesByKeyword(String keyword) throws SQLException {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM [Property] WHERE name LIKE ? OR address LIKE ?";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                PropertyImageDAOImpl pImgDao = new PropertyImageDAOImpl();
                PropertyTypeDAOImpl pTypeDao = new PropertyTypeDAOImpl();
                PropertyStatusDAO pStatusDao = new PropertyStatusDAO();
                PropertyUtilityDAOImpl pUtilityDao = new PropertyUtilityDAOImpl();
                UserDAOImpl udb = new UserDAOImpl();
                Property property = new Property();
                property.setId(result.getInt("property_id"));
                property.setName(result.getString("name"));
                property.setHost(udb.getUserById(result.getInt("host_id")));
                property.setAddress(result.getString("address"));
                property.setArea(result.getDouble("area"));
                property.setPrice(result.getDouble("price"));
                property.setTotal(result.getInt("total"));
                property.setUtilities(pUtilityDao.getUtilitiesByPID(result.getInt("property_id")));
                property.setCreatedDate(result.getDate("created_date"));
                property.setStatus(pStatusDao.getStatusByID(result.getInt("pstatus_id")));
                property.setType(pTypeDao.getTypeByID(result.getInt("type_id")));
                property.setDescription(result.getString("description"));
                property.setImages(pImgDao.getImagesByPID(result.getInt("property_id")));
                list.add(property);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }
        return list;
    }

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<Property> getPropertiesByOwner(int uid) throws Exception {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM Property WHERE host_id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                PropertyImageDAOImpl pImgDao = new PropertyImageDAOImpl();
                PropertyTypeDAOImpl pTypeDao = new PropertyTypeDAOImpl();
                PropertyStatusDAO pStatusDao = new PropertyStatusDAO();
                PropertyUtilityDAOImpl pUtilityDao = new PropertyUtilityDAOImpl();
                UserDAOImpl udb = new UserDAOImpl();
                Property property = new Property();
                property.setId(result.getInt("property_id"));
                property.setName(result.getString("name"));
                property.setHost(udb.getUserById(result.getInt("host_id")));
                property.setAddress(result.getString("address"));
                property.setArea(result.getDouble("area"));
                property.setPrice(result.getDouble("price"));
                property.setTotal(result.getInt("total"));
                property.setUtilities(pUtilityDao.getUtilitiesByPID(result.getInt("property_id")));
                property.setCreatedDate(result.getDate("created_date"));
                property.setStatus(pStatusDao.getStatusByID(result.getInt("pstatus_id")));
                property.setType(pTypeDao.getTypeByID(result.getInt("type_id")));
                property.setDescription(result.getString("description"));
                property.setImages(pImgDao.getImagesByPID(result.getInt("property_id")));
                list.add(property);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }
        return list;
    }

    /**
     *
     * @param tid
     * @return
     * @throws Exception
     */
    @Override
    public List<Property> getPropertiesByType(int tid) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param keyword
     * @param lastestTime
     * @param lowestPrice
     * @param maxPrice
     * @param minPrice
     * @param area
     * @return
     * @throws Exception
     */
    @Override
    public List<Property> getPropertiesByFiter(String keyword, int lastestTime, int lowestPrice, double maxPrice, double minPrice, double area) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param pid
     * @return
     * @throws Exception
     */
    @Override
    public Property getPropertyById(int pid) throws Exception {
        Property property = new Property();
        String sql = "SELECT * FROM [Property] WHERE property_id=?";

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, pid);
            result = statement.executeQuery();

            if (result.next()) {
                UserDAOImpl udb = new UserDAOImpl();
                PropertyImageDAOImpl pImgDao = new PropertyImageDAOImpl();
                PropertyTypeDAOImpl pTypeDao = new PropertyTypeDAOImpl();
                PropertyStatusDAO pStatusDao = new PropertyStatusDAO();
                PropertyUtilityDAOImpl pUtilityDao = new PropertyUtilityDAOImpl();
                property.setId(result.getInt("property_id"));
                property.setName(result.getString("name"));
                property.setHost(udb.getUserById(result.getInt("host_id")));
                property.setAddress(result.getString("address"));
                property.setArea(result.getDouble("area"));
                property.setPrice(result.getDouble("price"));
                property.setTotal(result.getInt("total"));
                property.setUtilities(pUtilityDao.getUtilitiesByPID(result.getInt("property_id")));
                property.setCreatedDate(result.getDate("created_date"));
                property.setStatus(pStatusDao.getStatusByID(result.getInt("pstatus_id")));
                property.setType(pTypeDao.getTypeByID(result.getInt("type_id")));
                property.setDescription(result.getString("description"));
                property.setImages(pImgDao.getImagesByPID(result.getInt("property_id")));
                return property;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }
        return null;
    }

    /**
     *
     * @param newProperty
     * @return
     * @throws SQLException
     */
    @Override
    public int insertProperty(Property newProperty) throws SQLException {
        String sql = "INSERT INTO Property (name, host_id, address, description, "
                + "price, area, created_date, total, pstatus_id, type_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newProperty.getName());
            statement.setInt(2, newProperty.getHost().getId());
            statement.setString(3, newProperty.getAddress());
            statement.setString(4, newProperty.getDescription());
            statement.setDouble(5, newProperty.getPrice());
            statement.setDouble(6, newProperty.getArea());
            statement.setDate(7, newProperty.getCreatedDate());
            statement.setInt(8, newProperty.getTotal());
            statement.setInt(9, 1);
            statement.setInt(10, newProperty.getType().getId());

            statement.executeUpdate();
            result = statement.getGeneratedKeys();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }

        return -1;
    }

    /**
     *
     * @param pid
     * @return
     * @throws Exception
     */
    @Override
    public int getAvailableByPID(int pid) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int getAvailableProperty(int uid) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int getNumberOfProperty(int uid) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int getNumberOfRentedProperty(int uid) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param list
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    @Override
    public List<Property> getPropertyByPage(List<Property> list, int start, int end) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<Property> getPropertyByHostId(int id) throws Exception {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM [Property] WHERE host_id=?";

        PreparedStatement statement = null;
        ResultSet result = null;
        Connection connection = getConnection();

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                PropertyImageDAOImpl pImgDao = new PropertyImageDAOImpl();
                PropertyTypeDAOImpl pTypeDao = new PropertyTypeDAOImpl();
                PropertyStatusDAO pStatusDao = new PropertyStatusDAO();
                PropertyUtilityDAOImpl pUtilityDao = new PropertyUtilityDAOImpl();
                UserDAOImpl udb = new UserDAOImpl();
                Property property = new Property();
                property.setId(result.getInt("property_id"));
                property.setName(result.getString("name"));
                property.setHost(udb.getUserById(result.getInt("host_id")));
                property.setAddress(result.getString("address"));
                property.setArea(result.getDouble("area"));
                property.setPrice(result.getDouble("price"));
                property.setTotal(result.getInt("total"));
                property.setUtilities(pUtilityDao.getUtilitiesByPID(result.getInt("property_id")));
                property.setCreatedDate(result.getDate("created_date"));
                property.setStatus(pStatusDao.getStatusByID(result.getInt("pstatus_id")));
                property.setType(pTypeDao.getTypeByID(result.getInt("type_id")));
                property.setDescription(result.getString("description"));
                property.setImages(pImgDao.getImagesByPID(result.getInt("property_id")));
                list.add(property);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }

        return list;
    }
    

    /**
     *
     * @param id
     * @throws Exception
     */
    public void deletePropertyByID(int id) throws Exception {
        String strDelete = "DELETE FROM [dbo].[Property]\n"
                + "      WHERE property_id=?";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(strDelete);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            close(connection, statement, null);
        }
    }

    /**
     *
     * @param hostId
     * @return
     * @throws Exception
     */
    public Map<Property, Integer> getTrendingRentProperty(int hostId) throws Exception {
        Map<Property, Integer> map = new HashMap<>();
        String sql = "SELECT p.property_id, COUNT(*) as total FROM Contract c\n"
                + "INNER JOIN Property p\n"
                + "ON c.property_id = p.property_id\n"
                + "WHERE p.host_id = ? \n"
                + "GROUP BY p.property_id\n"
                + "ORDER BY total DESC";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, hostId);
            result = statement.executeQuery();
            
            while (result.next()) {
                map.put(getPropertyById(result.getInt("property_id")), result.getInt("total"));
            }
            
            return map;
        } catch (Exception ex) {
            throw ex;
        } finally {
            close(connection, statement, result);
        }
    }

    @Override
    public void updateProperty(Property newProperty) throws Exception{
       String sql = "UPDATE Property name=?, address=?, description=?, "
                + "price=?, area=?, created_date=?, total=?, pstatus_id=?, type_id=?"
                + "WHERE property_id=?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newProperty.getName());            
            statement.setString(2, newProperty.getAddress());
            statement.setString(3, newProperty.getDescription());
            statement.setDouble(4, newProperty.getPrice());
            statement.setDouble(5, newProperty.getArea());
            statement.setDate(6, newProperty.getCreatedDate());
            statement.setInt(7, newProperty.getTotal());
            statement.setInt(8, 1);
            statement.setInt(9, newProperty.getType().getId());

            statement.executeUpdate();           

            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            close(connection, statement, null);
        }       
    }

}
