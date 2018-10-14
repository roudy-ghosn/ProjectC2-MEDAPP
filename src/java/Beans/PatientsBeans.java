package Beans;

import BusinessObjects.Patient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PatientsBeans implements Serializable {
    
    private List<Patient> patientsList;
    
    public PatientsBeans(){
        patientsList = new ArrayList<Patient>();
    }
    
    public List<Patient> getPatientsList(){
        return patientsList;
    }
    
    public void setPatientsList(ArrayList<Patient> patientsList){
        this.patientsList = patientsList;
    }
    
    public void getPatientsList(String filter){
        //patientsList = QueryUtils.getPatientsList(filter);
    }
    
    public void onLoad(){
        getPatientsList(null);
    }
}
