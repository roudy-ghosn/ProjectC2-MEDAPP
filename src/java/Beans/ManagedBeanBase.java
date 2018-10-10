package Beans;

import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ManagedBeanBase {

    public boolean isLoggedInUserAdmin() {
        return SessionUtils.isUserAdmin();
    }

    public boolean isLoggedInUserDoctor() {
        return SessionUtils.isUserDoctor();
    }

    public boolean isLoggedInUserPatient() {
        return SessionUtils.isUserPatient();
    }
}
