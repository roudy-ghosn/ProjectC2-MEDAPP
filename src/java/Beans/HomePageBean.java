package Beans;

import CommonUtils.SessionUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class HomePageBean {
    
    public String getUserFullname(){
        return SessionUtils.getFirstName() + " " + SessionUtils.getLastName();
    }
    
}
