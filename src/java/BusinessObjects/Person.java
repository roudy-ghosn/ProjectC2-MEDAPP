package BusinessObjects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private String fatherName;
    private String motherName;
    private boolean isMaried;
    private boolean hasChildren;
    private String address;
    private String country;
    private String region;
    private String zipCode;
    private String action;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public boolean isIsMaried() {
        return isMaried;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setIsMaried(boolean isMaried) {
        this.isMaried = isMaried;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        if (getDateOfBirth() != null) {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int d1 = Integer.parseInt(formatter.format(getDateOfBirth()));
            int d2 = Integer.parseInt(formatter.format(new Date()));
            return (d2 - d1) / 10000;
        }
        return null;
    }
}
