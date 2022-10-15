/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * Sep 28, 2022         1.0           DucPTMHE160517     First Implement
 */
package dal;

import java.sql.SQLException;
import model.PropertyType;

/**				
 * The class contains method find update, delete, insert property type to/from DB			
 * 				
 * The method will throw an object  of <code>java.lang.Exception</code> class if 
 * there is any error occurring when finding, inserting, or updating data				
 * <p>Bugs: Haven't found yet				
 *				
 * @author DucPTMHE160517				
 */
public interface IPropertyTypeDAO {

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public PropertyType getTypeByID(int id) throws SQLException;
}
