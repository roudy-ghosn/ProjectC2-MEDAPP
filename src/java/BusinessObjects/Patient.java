package BusinessObjects;

public class Patient extends Person {

    private MedicalFile medicalFile;
    private Doctor responsibleObserver;

    public Patient(Person person) {
        
    }

    public MedicalFile getMedicalFile() {
        return medicalFile;
    }

    public void setMedicalFile(MedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public void setResponsibleObserver(Doctor responsibleObserver) {
        this.responsibleObserver = responsibleObserver;
    }

    public Doctor getResponsibleObserver() {
        return responsibleObserver;
    }
}
