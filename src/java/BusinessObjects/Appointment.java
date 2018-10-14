/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import java.util.Date;

/**
 *
 * @author user
 */
public class Appointment {

    
    private String id;
    private Doctor doctor;
    private Patient patient;
    private Date date;
    private String time;
    private String notes;

    public String getId() {
        return id;
    }
    
    
    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date creationDate) {
        this.date = date;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
     public Patient getPatient(){
        return patient;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }

}
