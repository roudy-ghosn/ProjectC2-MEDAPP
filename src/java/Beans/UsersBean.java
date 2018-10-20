package Beans;

import BusinessObjects.Person;
import BusinessObjects.Role;
import BusinessObjects.User;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UsersBean implements Serializable {

    private User user;
    private List<User> usersList;

    public UsersBean() {
        user = new User();
        usersList = new ArrayList<User>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public void getUsersList(String filter) {
        usersList = QueryUtils.getUsersList(filter);
    }

    public String getUserIdFromURL() {
        return SessionUtils.getRequest().getParameter("userId");
    }

    public boolean isUserDetailsMode() {
        return getUserIdFromURL() != null;
    }

    public boolean isCreateMode() {
        return SessionUtils.getRequest().getParameter("createMode") != null;
    }

    public boolean isDisplayMode() {
        return !isCreateMode() && !isUserDetailsMode();
    }

    public void getSpecifiedUserDetails(String id) {
        List<User> users = QueryUtils.getUsersList(id);
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
    }

    public void onLoad() {
        if (isUserDetailsMode()) {
            getSpecifiedUserDetails(getUserIdFromURL());
        } else {
            getUsersList(null);
        }
    }

    public void addNewUser() {
        User user = new User();
        user.setAction("C");
        user.setCreationDate(new Date());
        usersList.add(user);
    }

    public void deleteUser(String userId) {
        QueryUtils.deleteUser(userId);
    }

    public String save() {
        if (user.getId() != null) {
            QueryUtils.updateUser(user);
        } else {
            QueryUtils.insertUser(user);
        }
        return "users?faces-redirect=true";
    }

    /* LOVS */
    public List<Role> getRolesList() {
        return QueryUtils.getRolesList(null);
    }

    public List<Person> getPersonsList() {
        return QueryUtils.getPersonList(null);
    }
}
