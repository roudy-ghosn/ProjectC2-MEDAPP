package BusinessObjects;

public class Doctor extends Person {
    
    private String ln;
    private String lg;
    private String specialty;

    public void setLn(String ln) {
        this.ln = ln;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getLn() {
        return ln;
    }

    public String getLg() {
        return lg;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }    
}
