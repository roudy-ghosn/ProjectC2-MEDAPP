package Beans;

import BusinessObjects.Appointment;
import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import BusinessObjects.MedicalFile;
import CommonUtils.QueryUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HomePageBean implements Serializable {

    private MedicalFile medicalFile;
    private List<Appointment> appointmentList;

    public MedicalFile getMedicalFile() {
        System.out.println(medicalFile);
        return medicalFile;
    }

    public void setMedicalFile(MedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public HomePageBean() {
        medicalFile = new MedicalFile();
    }

    public void getPatientMedicalFile() {
        setMedicalFile(QueryUtils.getPatientMedicalFile(SessionUtils.getLoggedPersonId()));
    }

    public void onLoad() {
        if (SessionUtils.isUserPatient()) {
            getPatientMedicalFile();
        } else if (SessionUtils.isUserDoctor()) {
            getTodayAppointment();
        }
    }

    public String getUserFullname() {
        return SessionUtils.getFirstName() + " " + SessionUtils.getLastName();
    }

    public void getTodayAppointment() {
        setAppointmentList(QueryUtils.getAppointmentList(SessionUtils.getLoggedPersonId()));
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public LocalDate getTodaysDate() {
        return LocalDate.now();
    }
}
