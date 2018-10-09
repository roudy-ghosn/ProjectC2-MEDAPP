package BusinessObjects;

public class Disease {
    
    private String id;
    private String type;
    private String note;
    private String description;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
