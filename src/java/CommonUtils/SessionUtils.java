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
        if (getSession() != null) {
            return getSession().getAttribute("username").toString();
        }
        return null;
    }

    public static void setFirstName(String firstname) {
        getSession().setAttribute("firstname", firstname);
    }

    public static String getFirstName() {
        if (getSession() != null) {
            return getSession().getAttribute("firstname").toString();
        }
        return null;
    }

    public static void setLastName(String lastname) {
        getSession().setAttribute("lastname", lastname);
    }

    public static String getLastName() {
        if (getSession() != null) {
            return getSession().getAttribute("lastname").toString();
        }
        return null;
    }

    public static void setRole(String role) {
        getSession().setAttribute("role", role);
    }

    public static String getRole() {
        if (getSession() != null) {
            return getSession().getAttribute("role").toString();
        }
        return null;
    }

    public static void setLoggedPersonId(String loggedPersonId) {
        getSession().setAttribute("loggedPersonId", loggedPersonId);
    }

    public static String getLoggedPersonId() {
        if (getSession() != null) {
            return getSession().getAttribute("loggedPersonId").toString();
        }
        return null;
    }

    public static boolean isUserAdmin() {
        return "Administrator".equals(getRole());
    }

    public static boolean isUserDoctor() {
        return "Doctor".equals(getRole());
    }

    public static boolean isUserPatient() {
        return "Patient".equals(getRole());
    }
}
