package BusinessObjects;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String id;
    private String person;
    private String username;
    private String password;
    private Date   creationDate;
    private String role;
    private String action;

    public String getId() {
        return id;
    }

    public String getPerson() {
        return person;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
