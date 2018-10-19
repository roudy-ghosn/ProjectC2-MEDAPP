package Beans;

import BusinessObjects.Person;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ProfileBean implements Serializable {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ProfileBean() {
        person = new Person();
    }

    public String getImageURL() {
        if (person != null) {
            switch (person.getGender()) {
                case "M":
                    return "/Images/male-pp.jpg";
                case "F":
                    return "/Images/female-pp.jpg";
            }
        }
        return null;
    }

    public void getLoggedInUserDetails() {
        List<Person> persons = QueryUtils.getPersonList(SessionUtils.getLoggedPersonId());
        if (persons != null && persons.size() > 0) {
            person = persons.get(0);
        }
    }

    public void onLoad() {
        getLoggedInUserDetails();
    }

    public String save() {
        // QueryUtils.updateDoctor(doctor);
        return "profile?faces-redirect=true";
    }
}
