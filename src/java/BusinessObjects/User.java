package BusinessObjects;

import java.util.Date;

public class User {

    private String id;
    private Person person;
    private String username;
    private String password;
    private Date   creationDate;
    private Role   role;

    public String getId() {
        return id;
    }

    public Person getPerson() {
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

    public Role getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson(Person person) {
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

    public void setRole(Role role) {
        this.role = role;
    }

}
