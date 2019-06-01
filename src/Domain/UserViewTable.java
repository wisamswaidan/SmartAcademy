package Domain;

public class UserViewTable {
    String FirstName, LastName, TelephoneNumber, Address, Zipcode, UserName, Password, UserType;

    public UserViewTable(String firstName, String lastName, String telephoneNumber, String address, String zipcode, String userName, String password, String userType) {
        FirstName = firstName;
        LastName = lastName;
        TelephoneNumber = telephoneNumber;
        Address = address;
        Zipcode = zipcode;
        UserName = userName;
        Password = password;
        UserType = userType;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
