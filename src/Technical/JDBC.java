package Technical;

import Foundation.DB;

/**
 * All the JDBC command we used.
 */
public class JDBC {
    /*
    Here we need to add the JDBC commands to Add , Edit and Delete from Database
     */

    /**
     * Sql Statement to check the username and the password from database , use it for Unit Testing.
     * @param userName The username.
     * @param passWord The password.
     */

    public static boolean loginTSQL(String userName , String passWord){

        //SQL_Query to read the user name and the password from the database .
        DB.selectSQL("SELECT * from tbl_Users where fld_UserName = '"+ userName +"' and fld_Password = '"+ passWord +"'");
        do
        {
            String result = DB.getDisplayData();
            if (result.equals(DB.NOMOREDATA))
            {
                return false;
            }
            else
            {
                return true;
            }
        } while (true);
    }


    /**
     * All the JDBC command to related to the users.
     */

    public String CreateUserTSQL(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_Users VALUES( ? ,  ? , ?  , ? , ? , ? , ? ,? )";
        return sql;
    }

    public String ViewUserTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Users ";
        return sql;
    }

    public String DeleteUserTSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_Users WHERE fld_UserName = ? ";
        return sql;
    }

    public String UpdateUserTSQL(){

        //SQL_Query to......
        String sql = "UPDATE tbl_Users SET fld_FirstName = ? , fld_LastName = ? , fld_TelephoneNumber = ? , fld_Address  = ? , fld_Zipcode = ? , fld_Password = ? , fld_UserType = ? where fld_UserName = ? ";

        return sql;
    }


    /**
     * All the JDBC command to related to the Educations.
     */

    public String ViewEducationTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Educations";
        return sql;
    }

    public String EditEducationTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Educations where fld_AMU =?";
        return sql;
    }

    public String DeleteEducationTSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_Educations WHERE fld_AMU=?";

        return sql;
    }

    public String UpdateEducationTSQL(){

        //SQL_Query to......
        String sql = "UPDATE tbl_Educations SET fld_Title = ? , fld_Information = ? , fld_NumOfDays = ? , fld_Provider  = ? , fld_Type = ? where fld_AMU = ?";

        return sql;
    }


    /**
     * All the JDBC command to related to the Companies.
     */
    public String CreateCompanyTSQL(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_Companies VALUES (? , ? , ? , ? , ? , ? , ? , ?)";
        return sql;
    }

    public String ViewCompanyTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Companies";
        return sql;
    }

    public String EditCompanyTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Companies where fld_CompanyID =?";
        return sql;
    }

    public String DeleteCompanyTSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_Companies WHERE fld_CompanyID=?";
        return sql;
    }

    public String UpdateCompanyTSQL(){

        //SQL_Query to......
        String sql = "UPDATE tbl_Companies SET fld_Name = ? , fld_CVR = ? , fld_TelephoneNumber = ? , fld_Address  = ? , fld_Zipcode = ?  , fld_NumberOfEmployees = ? , fld_Information = ? where fld_CompanyID = ?";

        return sql;
    }


    /**
     * All the JDBC command to related to the Employees.
     */
    public String CreateEmployeeTSQL(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_CompanyEmployees VALUES (? , ? , ? , ? , ? , ? , ? , ?)";
        return sql;
    }

    public String ViewEmployeeTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_CompanyEmployees";
        return sql;
    }

    public String EditEmployeeTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_CompanyEmployees where fld_Mobile =?";
        return sql;
    }

    public String DeleteEmployeeTSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_CompanyEmployees WHERE fld_Mobile=?";
        return sql;
    }

    public String UpdateEmpTSQL(){

        //SQL_Query to......
        String sql = "UPDATE tbl_CompanyEmployees SET fld_FirstName = ? , fld_LastName = ? , fld_Email = ? , fld_Address  = ? , fld_Zipcode = ? ,fld_Information = ? , fld_CompanyID =? WHERE fld_Mobile = ?";
        return sql;
    }


    /**
     * All the JDBC command to related to the Assign a User.
     */
    public String CreateAssignTSQL(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_AssignUsers VALUES (? , ? )";
        return sql;
    }

    public String ViewAssignTSQL(){

        //SQL_Query to......
        String sql = "select * from tbl_AssignUsers";
        return sql;
    }

    public String DeleteAssignTSQL(){
        //SQL_Query to......
        String sql = "DELETE FROM tbl_AssignUsers WHERE fld_ComapnyID=? AND fld_UserName =?";
        return sql;
    }


    /**
     * All the JDBC command to related to the EducationSch .
     */
    public String CreateEducationSchTSQL(){

        //SQL_Query to......
       // String sql = "INSERT INTO tbl_EduSchGenerate VALUES ? INSERT INTO tbl_EducationSchedule VALUES (?,?,?)";
        String sql = "INSERT INTO tbl_EducationSchedule VALUES (?,?,?)";

        return sql;
    }

    public String ViewEducationSchTSQL(){

        //SQL_Query to......
        String sql = "select * from tbl_EducationSchedule";
        return sql;
    }

    public String DeleteEducationSchSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_EducationSchedule WHERE fld_EduSch_ID = ?";
        return sql;
    }

    public String CreateEducationSchID(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_EduSchGenerate (fld_EduSch_ID) VALUES (?);";
        return sql;
    }

    public String DeleteEducationSchID(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_EduSchGenerate WHERE fld_EduSch_ID=?";
        return sql;
    }


    /**
     * All the JDBC command to related to the Education plan.
     */
    public String CreateEducationplanTSQL(){

        //SQL_Query to......
        String sql = "INSERT INTO tbl_EducationPlans VALUES (? , ? , ? , ? , ? , ?)";
        return sql;
    }

    public String ViewEducationplanTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_EducationPlans";
        return sql;
    }

    public String DeleteEducationplanTSQL(){

        //SQL_Query to......
        String sql = "DELETE FROM tbl_EducationPlans WHERE fld_PlanID=?";
        return sql;
    }



}
