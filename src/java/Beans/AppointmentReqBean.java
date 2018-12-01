/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessObjects.AppointmentReq;
import BusinessObjects.Doctor;
import BusinessObjects.User;
import CommonUtils.SessionUtils;
import DataBase.DBConfig;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author User
 */

@ManagedBean
@ViewScoped
public class AppointmentReqBean implements Serializable  {
    
    private static PreparedStatement statement;
    private static DBConfig dbConfig = new DBConfig();
    private static Connection dbConnection = dbConfig.getConnection();
    public static AppointmentReq appReq;
    
    public void setAppReq(AppointmentReq appReq) {
        this.appReq = appReq;
    }  
    
     public static String getPatientIdFromURL() {
        return SessionUtils.getLoggedPersonId();
    }

    
     public AppointmentReq getAppointmentReq() {
        String query = "SELECT `Id`, `Patient_Id`, `Doctor_Id`, `Appointment_type`, `Description`, `Date`, `Time`, `Additional_Notes` FROM `AppointmentReq` "
                + "WHERE Id = ?";
        
        try {
             statement = dbConnection.prepareStatement(query);
            statement.setString(1, "-1");
            ResultSet result = statement.executeQuery();
            appReq=new AppointmentReq();
            while (result.next()) {
               appReq.setAppointmentId(result.getInt("Id"));
               appReq.setdoctorId(result.getInt("Doctor_Id"));
               appReq.setState(result.getString("Appointment_type"));
               appReq.setAppDate(result.getDate("Date"));
               appReq.setAppTime(result.getTime("Time"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            NewMode();
        }
        return appReq;
    }  
     public void onLoad() {
       NewMode();
    }
     private static void NewMode(){
         appReq=new AppointmentReq();
         int patientId=Integer.parseInt(getPatientIdFromURL());
         appReq.setpatientId(patientId);
         
     }
     public void save(){
         insertAppReq(appReq);
     }
    
     public void updateAppReq(AppointmentReq appReq) {
        String query = "UPDATE `AppointmentReq` \n" +
            "	SET \n" +
            "	`Appointment_type`=?,\n" +
            "	`Description`=?,\n" +
            "	`Additional_Notes`=? \n" +
            "	WHERE `Id`=?";
        try {
            int i=1;
            statement = dbConnection.prepareStatement(query);
            statement.setString(i++, appReq.getType());
            statement.setString(i++, appReq.getState());
            statement.setString(i++, "");
            statement.setInt(i++, appReq.getAppointmentId());
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public void insertAppReq(AppointmentReq appReq) {
        String query = "INSERT INTO `AppointmentReq`(`Patient_Id`, `Doctor_Id`, `Appointment_type`, `Description`, `Date`, `Time`, `Additional_Notes`) \n" +
            "	VALUES (?,?,?,?,?,?,?)";
        try {
            int i=1;
            statement = dbConnection.prepareStatement(query);
            statement.setInt(i++, appReq.getPatientId());
            statement.setInt(i++, appReq.getDoctorId());
            statement.setString(i++, appReq.getType());
            statement.setString(i++, appReq.getState());
            statement.setDate(i++, appReq.getAppDate());
            statement.setTime(i++, appReq.getAppTime());
            statement.setString(i++, "");
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
