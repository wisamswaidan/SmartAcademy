package Domain;

public class EducationViewTable {
    String AMU , Title , Provider , NumOfDays , Type , information;

    public EducationViewTable(String AMU, String title, String provider, String numOfDays, String type, String information) {
        this.AMU = AMU;
        Title = title;
        Provider = provider;
        NumOfDays = numOfDays;
        Type = type;
        this.information = information;
    }

    public String getAMU() {
        return AMU;
    }

    public String getTitle() {
        return Title;
    }

    public String getProvider() {
        return Provider;
    }

    public String getNumOfDays() {
        return NumOfDays;
    }

    public String getType() {
        return Type;
    }

    public String getInformation() {
        return information;
    }


    public void setAMU(String AMU) {
        this.AMU = AMU;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public void setNumOfDays(String numOfDays) {
        NumOfDays = numOfDays;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
