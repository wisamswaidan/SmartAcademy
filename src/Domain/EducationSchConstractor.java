package Domain;

public class EducationSchConstractor {
    String ID , AMU , Date;

    public EducationSchConstractor(String ID, String AMU, String date) {
        this.ID = ID;
        this.AMU = AMU;
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public String getAMU() {
        return AMU;
    }

    public String getDate() {
        return Date;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAMU(String AMU) {
        this.AMU = AMU;
    }

    public void setDate(String date) {
        Date = date;
    }
}
