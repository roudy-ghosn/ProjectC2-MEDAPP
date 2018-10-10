package Beans;

import BusinessObjects.Person;
import BusinessObjects.Role;
import BusinessObjects.User;
import CommonUtils.QueryUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsersBean {

    private List<User> usersList;
    private List<User> deletedUsersList;

    public UsersBean() {
        usersList = new ArrayList<User>();
        deletedUsersList = new ArrayList<User>();
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<User> getDeletedUsersList() {
        return deletedUsersList;
    }

    public void setDeletedUsersList(List<User> deletedUsersList) {
        this.deletedUsersList = deletedUsersList;
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
        Iterator<User> userIterator = getUsersList().iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();

            if (userId.equals(user.getId())) {
                deletedUsersList.add(user);
                userIterator.remove();
            }
        }
    }

    public void save() {
        for (User user : getDeletedUsersList()) {
            QueryUtils.deleteUser(user.getId());
        }
        for (User user : getUsersList()) {
            if ("C".equals(user.getAction())) {
                QueryUtils.insertUser(user);
            } else {
                QueryUtils.updateUser(user);
            }
        }
        getUsersList();
    }

    /* LOVS */
    public List<Role> getRolesList() {
        return QueryUtils.getRolesList(null);
    }

    public List<Person> getPersonsList() {
        return QueryUtils.getPersonList(null);
    }
}
