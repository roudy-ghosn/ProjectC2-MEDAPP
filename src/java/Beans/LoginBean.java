package Beans;

import CommonUtils.JsfUtils;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    public String getErrorText() {
        return (String) JsfUtils.getExpressionValue("#{viewScope.errorText}");
    }

    public void setErrorText(String errorText) {
        JsfUtils.setExpressionValue("#{viewScope.errorText}", errorText);
    }

    public String getUsername() {
        return (String) JsfUtils.getExpressionValue("#{viewScope.username}");
    }

    public void setUsername(String username) {
        JsfUtils.setExpressionValue("#{viewScope.username}", username);
    }

    public String getPassword() {
        return (String) JsfUtils.getExpressionValue("#{viewScope.password}");
    }

    public void setPassword(String password) {
        JsfUtils.setExpressionValue("#{viewScope.password}", password);
    }

    public String login() {
        if (QueryUtils.validateLogin(getUsername(), getPassword())) {
            return "homepage?faces-redirect=true";
        } else {
            setErrorText("Login Failed! Incorrect username/passowrd, Please try again");
        }
        return null;
    }
    
    public String logout() {
        SessionUtils.getSession().invalidate();
        return "login";
    }
}
