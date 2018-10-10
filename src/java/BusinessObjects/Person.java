package BusinessObjects;

public class Person {
    
    private String id;
    private String firstName;
    private String lastName;
    private String age;
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

    public String getAge() {
        return age;
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

    public void setAge(String age) {
        this.age = age;
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
}