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
    private List<Patient> deletedPatientsList;

    public PatientsBeans() {
        patientsList = new ArrayList<Patient>();
        deletedPatientsList = new ArrayList<Patient>();
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

    public void setDeletedPatientsList(List<Patient> deletedPatientsList) {
        this.deletedPatientsList = deletedPatientsList;
    }

    public List<Patient> getDeletedPatientsList() {
        return deletedPatientsList;
    }

    public void getPatientsList(String filter) {
        patientsList = QueryUtils.getPatientList(filter);
    }

    public void onLoad() {
        if (isPatientDetailsRequested()) {
            getSpecifiedPatientDetails(getPatientIdFromURL());
        } else {
            getPatientsList(null);
        }
    }

    public String getPatientIdFromURL() {
        return SessionUtils.getRequest().getParameter("patientId");
    }

    public boolean isPatientDetailsRequested() {
        return getPatientIdFromURL() != null;
    }

    public void getSpecifiedPatientDetails(String id) {
        List<Patient> patients = QueryUtils.getPatientList(id);
        if (patients != null && patients.size() > 0) {
            this.patient = patients.get(0);
        }
    }

    public void deletePatient(String patientId) {
        Iterator<Patient> patientIterator = getPatientsList().iterator();
        while (patientIterator.hasNext()) {
            Patient pat = patientIterator.next();

            if (patientId.equals(pat.getId())) {
                deletedPatientsList.add(pat);
                patientIterator.remove();
            }
        }
    }

    public void addNewPatient() {
        Patient newPatient = new Patient();
        newPatient.setAction("C");
        patientsList.add(newPatient);
    }

    public void save() {
        if (patient != null) {
            // QueryUtils.updatePatient(patient);
        } else {
            for (Patient pat : getDeletedPatientsList()) {
                // QueryUtils.deletePatient(pat.getId());
            }
            for (Patient pat : getPatientsList()) {
                if ("C".equals(pat.getAction())) {
                    // QueryUtils.insertPatient(pat);
                } else {
                    // QueryUtils.updatePatient(pat);
                }
            }
            getPatientsList(null);
        }
    }
}
