/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

/**
 *
 * @author User
 */
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */

@ManagedBean
@SessionScoped

public class AppointmentReq implements Serializable {
    
    private static final long serialVersionUID = 1715935052239888761L;
	private int appointmentId;
	private int patientId;
	private int doctorId;
	private String type;
	private String state;
	private Date appDate;
	private Time appTime;
        private String pickDr;
        
        public AppointmentReq (){
            
        }
        
       	public AppointmentReq(int appointmentId,int patientId,int doctorId,String type, String state, Date appDate, Time appTime, String pickDr) {
            
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.type = type;
		this.state = state;
		this.appDate = appDate;
		this.appTime = appTime;
                this.pickDr = pickDr;
	}
         public void setpatientId(int Id){
           this.patientId=Id;
        } public void setdoctorId(int Id){
           this.doctorId=Id;
        }
         public void setAppointmentId(int Id){
           this.appointmentId=Id;
        }
        public int getAppointmentId(){
            return appointmentId;
        }
       public int getPatientId(){
           return patientId;
       }
       public int getDoctorId(){
       
           return doctorId;
       }

	public String getType() {
		return type;
	}
        

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Time getAppTime() {
		return appTime;
	}

	public void setAppTime(Time appTime) {
		this.appTime = appTime;
	}
        
        public void setPickDr(String pickDr) {
		this.pickDr = pickDr;
	}
        
}