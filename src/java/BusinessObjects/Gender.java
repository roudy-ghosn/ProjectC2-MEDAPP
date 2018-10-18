package BusinessObjects;

public enum Gender {
    Male("M"),
    Female("F");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
