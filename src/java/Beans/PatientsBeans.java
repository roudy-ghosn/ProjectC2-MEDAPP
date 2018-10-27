package Beans;

import BusinessObjects.Patient;
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
public class PatientsBeans implements Serializable {

    private Patient patient;
    private List<Patient> patientsList;

    public PatientsBeans() {
        patient = new Patient();
        patientsList = new ArrayList<Patient>();
    }

    public List<Patient> getPatientsList() {
        return patientsList;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPatientsList(List<Patient> patientsList) {
        this.patientsList = patientsList;
    }

    public void getPatientsList(String filter) {
        patientsList = QueryUtils.getPatientList(filter);
    }

    public String getPatientIdFromURL() {
        return SessionUtils.getRequest().getParameter("patientId");
    }

    public boolean isPatientDetailsMode() {
        return getPatientIdFromURL() != null;
    }

    public boolean isCreateMode() {
        return SessionUtils.getRequest().getParameter("createMode") != null;
    }

    public boolean isDisplayMode() {
        return !isCreateMode() && !isPatientDetailsMode();
    }

    public void onLoad() {
        if (isPatientDetailsMode()) {
            getSpecifiedPatientDetails(getPatientIdFromURL());
        } else {
            getPatientsList(null);
        }
    }

    public void getSpecifiedPatientDetails(String id) {
        List<Patient> patients = QueryUtils.getPatientList(id);
        if (patients != null && patients.size() > 0) {
            this.patient = patients.get(0);
        }
    }

    public void deletePatient(String patientId) {
        QueryUtils.deletePerson(patientId);
        getPatientsList(null);
    }

    public void save() {
        if (patient.getId() != null) {
            QueryUtils.updatePatient(patient);
        } else {
            QueryUtils.insertPatient(patient);
        }
        getPatientsList(null);
    }
}
