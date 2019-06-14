package Domain;

public class EmployeeConstructor {
    String Firstname , Lastname , Email , Mobile , Address , Zipcode , Information , CompanyID;

    public EmployeeConstructor(String firstname, String lastname, String email, String mobile, String address, String zipcode, String information, String companyID) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Mobile = mobile;
        Address = address;
        Zipcode = zipcode;
        Information = information;
        CompanyID = companyID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getAddress() {
        return Address;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public String getInformation() {
        return Information;
    }

    public String getCompanyID() {
        return CompanyID;
    }


    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }
}
