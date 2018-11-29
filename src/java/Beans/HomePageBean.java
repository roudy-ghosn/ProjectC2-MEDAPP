package Beans;

import BusinessObjects.Appointment;
import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import BusinessObjects.MedicalFile;
import CommonUtils.QueryUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class HomePageBean implements Serializable {

    private MedicalFile medicalFile;
    private List<Appointment> appointmentList;
    private UIComponent backupButton;

    public MedicalFile getMedicalFile() {
        System.out.println(medicalFile);
        return medicalFile;
    }

    public void setMedicalFile(MedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public void setBackupButton(UIComponent backupButton) {
        this.backupButton = backupButton;
    }

    public UIComponent getBackupButton() {
        return backupButton;
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

    public void backupDatabase() {
        FacesMessage facesMessage;
        String[] result = QueryUtils.Backupdbtosql();
        if (result != null && "success".equals(result[1])) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Backup file successfully created under " + result[0], null);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Backup Failed!!", null);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(backupButton.getClientId(context), facesMessage);
    }

}
