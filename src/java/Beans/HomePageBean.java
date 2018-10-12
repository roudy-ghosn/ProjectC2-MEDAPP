package Beans;

import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import BusinessObjects.MedicalFile;
import CommonUtils.QueryUtils;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HomePageBean {

    private MedicalFile medicalFile;

    public MedicalFile getMedicalFile() {
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
        getPatientMedicalFile();
    }

    public String getUserFullname() {
        return SessionUtils.getFirstName() + " " + SessionUtils.getLastName();
    }
}
