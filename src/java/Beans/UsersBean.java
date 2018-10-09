package Beans;

import BusinessObjects.Role;
import BusinessObjects.User;
import CommonUtils.QueryUtils;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsersBean {

    private List<User> usersList;

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

    public void addUser() {
        usersList.add(new User());
    }

    public void deleteUser(String userId) {
        QueryUtils.deleteUser(userId);
    }

    public void editUser(String userId) {

    }
    
    public List<Role> getRolesList(){
        return QueryUtils.getRolesList(null);
    }
}
