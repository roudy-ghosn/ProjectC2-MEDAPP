package Beans;

import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class DoctorsBean {

    public String getDoctorIdFromURL() {
        return SessionUtils.getRequest().getParameter("doctorId");
    }

}
