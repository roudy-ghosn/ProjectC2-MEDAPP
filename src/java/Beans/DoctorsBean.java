package Beans;

import BusinessObjects.Doctor;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import com.mysql.jdbc.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DoctorsBean {

    private Doctor doctor;
    private List<Doctor> allDoctorsList;
    private List<Doctor> deletedDoctorsList;

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

    public List<Doctor> getDeletedDoctorsList() {
        return deletedDoctorsList;
    }

    public void setDeletedDoctorsList(List<Doctor> deletedDoctorsList) {
        this.deletedDoctorsList = deletedDoctorsList;
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

    public void addNewDoctor() {
        Doctor newDoctor = new Doctor();
        newDoctor.setAction("C");
        allDoctorsList.add(newDoctor);
    }

    public void deleteDoctor(String doctorId) {
        Iterator<Doctor> doctorIterator = getAllDoctorsList().iterator();
        while (doctorIterator.hasNext()) {
            Doctor doc = doctorIterator.next();

            if (doctorId.equals(doc.getId())) {
                deletedDoctorsList.add(doc);
                doctorIterator.remove();
            }
        }
    }

    public void save() {
        for (Doctor doc : getDeletedDoctorsList()) {
            QueryUtils.deleteDoctor(doc.getId());
        }
        for (Doctor doc : getAllDoctorsList()) {
            if ("C".equals(doc.getAction())) {
                QueryUtils.insertDoctor(doc);
            } else {
                QueryUtils.updateDoctor(doc);
            }
        }
        getAllDoctorsList(null);
    }
}
