package Domain;

public class CompanyConstructor {
    String ID , Name , CVR , TeleNumber , Address , Zipcode , NumOfEmplyees ,Information;

    public CompanyConstructor(String ID, String name, String CVR, String teleNumber, String address, String zipcode, String numOfEmplyees, String information) {
        this.ID = ID;
        Name = name;
        this.CVR = CVR;
        TeleNumber = teleNumber;
        Address = address;
        Zipcode = zipcode;
        NumOfEmplyees = numOfEmplyees;
        Information = information;
    }


    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getCVR() {
        return CVR;
    }

    public String getTeleNumber() {
        return TeleNumber;
    }

    public String getAddress() {
        return Address;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public String getNumOfEmplyees() {
        return NumOfEmplyees;
    }

    public String getInformation() {
        return Information;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCVR(String CVR) {
        this.CVR = CVR;
    }

    public void setTeleNumber(String teleNumber) {
        TeleNumber = teleNumber;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public void setNumOfEmplyees(String numOfEmplyees) {
        NumOfEmplyees = numOfEmplyees;
    }

    public void setInformation(String information) {
        Information = information;
    }
}
