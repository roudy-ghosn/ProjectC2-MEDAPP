package BusinessObjects;

import java.io.Serializable;

public class Role implements Serializable {
    
    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
