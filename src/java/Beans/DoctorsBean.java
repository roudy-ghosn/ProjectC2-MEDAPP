package Beans;

import BusinessObjects.Doctor;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DoctorsBean implements Serializable {

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

    public boolean isDoctorDetailsMode() {
        return getDoctorIdFromURL() != null;
    }

    public boolean isCreateMode() {
        return SessionUtils.getRequest().getParameter("createMode") != null;
    }

    public boolean isDisplayMode() {
        return !isCreateMode() && !isDoctorDetailsMode();
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

    public void deleteDoctor(String doctorId) {
        QueryUtils.deletePerson(doctorId);
        getAllDoctorsList(null);
    }

    public void onLoad() {
        if (isDoctorDetailsMode()) {
            getSpecifiedDoctorDetails(getDoctorIdFromURL());
        } else {
            getAllDoctorsList(null);
        }
    }

    public String save() {
        if (doctor.getId() != null) {
            QueryUtils.updateDoctor(doctor);
        } else {
            QueryUtils.insertDoctor(doctor);
        }
        return "doctors?faces-redirect=true";
    }
}
