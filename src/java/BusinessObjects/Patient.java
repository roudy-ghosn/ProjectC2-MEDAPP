package BusinessObjects;

public class Patient extends Person {

    private MedicalFile medicalFile;
    private String responsibleObserver;

//    public Patient(Person person) {
//        
//    }

    public MedicalFile getMedicalFile() {
        return medicalFile;
    }

    public void setMedicalFile(MedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public void setResponsibleObserver(String responsibleObserver) {
        this.responsibleObserver = responsibleObserver;
    }

    public String getResponsibleObserver() {
        return responsibleObserver;
    }
    
}
