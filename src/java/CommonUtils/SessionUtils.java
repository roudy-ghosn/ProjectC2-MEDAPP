package CommonUtils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static void setUserName(String username) {
        getSession().setAttribute("username", username);
    }

    public static String getUserName() {
        return getSession().getAttribute("username").toString();
    }

    public static void setFirstName(String firstname) {
        getSession().setAttribute("firstname", firstname);
    }

    public static String getFirstName() {
        return getSession().getAttribute("firstname").toString();
    }

    public static void setLastName(String lastname) {
        getSession().setAttribute("lastname", lastname);
    }

    public static String getLastName() {
        return getSession().getAttribute("lastname").toString();
    }

    public static void setRole(String role) {
        getSession().setAttribute("role", role);
    }

    public static String getRole() {
        return getSession().getAttribute("role").toString();
    }
}
