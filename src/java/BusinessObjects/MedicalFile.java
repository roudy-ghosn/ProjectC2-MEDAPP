package BusinessObjects;

import java.util.Date;
import java.util.List;

public class MedicalFile {
    
    private String id;
    private Doctor doctor;
    private Date creationDate;
    private String notes;
    private List<Report> reports;

    public String getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
