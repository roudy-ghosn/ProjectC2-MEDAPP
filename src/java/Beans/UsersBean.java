package Beans;

import BusinessObjects.Person;
import BusinessObjects.Role;
import BusinessObjects.User;
import CommonUtils.QueryUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.management.Query;

@ManagedBean
@SessionScoped
public class UsersBean {

    private List<User> usersList;

    public UsersBean() {
        usersList = new ArrayList<User>();
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

    public void onLoad() {
        getUsersList(null);
    }

    public void addNewUser() {
        User user = new User();
        user.setAction("C");
        user.setCreationDate(new Date());
        usersList.add(user);
    }

    public void deleteUser(String userId) {
        QueryUtils.deleteUser(userId);
        getUsersList(null);
    }

    /* LOVS */
    public List<Role> getRolesList() {
        return QueryUtils.getRolesList(null);
    }

    public List<Person> getPersonsList() {
        return QueryUtils.getPersonList(null);
    }

    public void save() {
        for (User user : getUsersList()) {
            if ("C".equals(user.getAction())) {
                QueryUtils.insertUser(user);
            } else {
                QueryUtils.updateUser(user);
            }
        }
        getUsersList();
    }
}
