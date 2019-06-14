package Domain;

public class EducationPlanConstractor {

    String PlanID , EducationAMU , EmployeeMobile , CompanyID , Information, EduSch_ID;

    public EducationPlanConstractor(String planID, String educationAMU, String employeeMobile, String companyID, String information, String eduSch_ID) {
        PlanID = planID;
        EducationAMU = educationAMU;
        EmployeeMobile = employeeMobile;
        CompanyID = companyID;
        Information = information;
        EduSch_ID = eduSch_ID;
    }

    public String getPlanID() {
        return PlanID;
    }

    public String getEducationAMU() {
        return EducationAMU;
    }

    public String getEmployeeMobile() {
        return EmployeeMobile;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public String getInformation() {
        return Information;
    }

    public String getEduSch_ID() {
        return EduSch_ID;
    }


    public void setPlanID(String planID) {
        PlanID = planID;
    }

    public void setEducationAMU(String educationAMU) {
        EducationAMU = educationAMU;
    }

    public void setEmployeeMobile(String employeeMobile) {
        EmployeeMobile = employeeMobile;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public void setEduSch_ID(String eduSch_ID) {
        EduSch_ID = eduSch_ID;
    }
}
