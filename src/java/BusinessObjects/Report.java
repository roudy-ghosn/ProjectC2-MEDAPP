package BusinessObjects;

import java.util.Date;

public class Report {

    private String id;
    private String title;
    private String description;
    private String attachment;
    private String info;
    private String notes;
    private String diagnosis;
    private Date   creationDate;

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

    public String getInfo() {
        return info;
    }

    public String getNotes() {
        return notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Date getCreationDate() {
        return creationDate;
    }

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

    public void setInfo(String info) {
        this.info = info;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
