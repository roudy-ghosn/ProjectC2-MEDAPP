package Beans;

import BusinessObjects.Doctor;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import com.mysql.jdbc.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DoctorsBean {

    private Doctor doctor;
    private List<Doctor> allDoctorsList;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setAllDoctorsList(List<Doctor> allDoctorsList) {
        this.allDoctorsList = allDoctorsList;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    
    public List<Doctor> getAllDoctorsList() {
        return allDoctorsList;
    }

    public DoctorsBean() {
        doctor = new Doctor();
        allDoctorsList = new ArrayList<Doctor>();
    }
    
    public String getDoctorIdFromURL() {
        return SessionUtils.getRequest().getParameter("doctorId");
    }

    public boolean isDoctorDetailsRequested() {
        return !StringUtils.isEmptyOrWhitespaceOnly(getDoctorIdFromURL());
    }

    public void getSpecifiedDoctorDetails(String id) {
        List<Doctor> doctors = QueryUtils.getDoctorList(id);
        if (doctors != null && doctors.size() > 0) {
            doctor = doctors.get(0);
        }
    }

    public void getAllDoctorsList(String filter) {
        allDoctorsList = QueryUtils.getDoctorList(null);
    }

    public void onLoad() {
        if (isDoctorDetailsRequested()) {
            getSpecifiedDoctorDetails(getDoctorIdFromURL());
        } else {
            getAllDoctorsList(null);
        }
    }
}
