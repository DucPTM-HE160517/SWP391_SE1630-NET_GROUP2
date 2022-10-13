/*
 * Copyright(C) 2022, FPT University.
 * Hostalpy
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 *Oct 6, 2022       1.0           LanBTHHE160676     First Implement
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import model.Request;
/**				
 * The class contains method find update, delete, insert staff information from				
 * Staff table in database. In the update or insert method, all data will be normalized (trim space) before update/insert into database				
 * The method wil throw an object  of <code>java.lang.Exception</code> class if there is any error occurring when finding, inserting, or updating data				
 * <p>Bugs: Haven't found yet				
 *				
 * @author LanBTHHE160676				
 */
public interface IRequestDAO {

    /**
     *
     * @param user_id
     * @param property_id
     * @param requestDate
     * @throws Exception
     */
    public void insertRequest(int user_id, int property_id, Date requestDate) throws Exception;

    /**
     *
     * @param request_id
     * @throws Exception
     */
    public void deleteRequestByRID(int request_id) throws Exception;

    /**
     *
     * @param request_id
     * @param newrstatus
     * @throws Exception
     */
    public void updateStatusByRID(int request_id, int newrstatus) throws Exception;

    /**
     *
     * @param request_id
     * @return
     * @throws Exception
     */
    public Request getRequestByRID(int request_id ) throws Exception;
}
