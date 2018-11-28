package BusinessObjects;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

    
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
        this.date = creationDate;
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
