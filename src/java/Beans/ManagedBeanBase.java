package Beans;

import BusinessObjects.Doctor;
import BusinessObjects.Gender;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ManagedBeanBase implements Serializable {

    public boolean isLoggedInUserAdmin() {
        return SessionUtils.isUserAdmin();
    }

    public boolean isLoggedInUserDoctor() {
        return SessionUtils.isUserDoctor();
    }

    public boolean isLoggedInUserPatient() {
        return SessionUtils.isUserPatient();
    }

    public String getGenderDescription(String gender) {
        if ("M".equals(gender)) {
            return Gender.Male.name();
        } else if("F".equals(gender)){
            return Gender.Female.name();
        }
        return null;
    }

    /* Common LOVs */
    public Gender[] getGenderListOfValues() {
        return Gender.values();
    }
    
    public List<Doctor> getDoctorsListOfValues(){
        return QueryUtils.getDoctorList(null);
    }
}
