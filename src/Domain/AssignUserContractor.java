package Domain;

public class AssignUserContractor {
    String Company , User;

    public AssignUserContractor(String company, String user) {
        Company = company;
        User = user;
    }

    public String getCompany() {
        return Company;
    }

    public String getUser() {
        return User;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public void setUser(String user) {
        User = user;
    }
}
