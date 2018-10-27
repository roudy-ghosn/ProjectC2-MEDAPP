package BusinessObjects;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {

    private String id;
    private Date   date;
    private String title;
    private String notes;
    private Doctor doctor;
    private String disease;
    private String comments;
    private String diagnosis;
    private String treatment;
    private String attachment;
    private String description;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getNotes() {
        return notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getComments() {
        return comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
