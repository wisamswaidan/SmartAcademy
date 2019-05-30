package Technical;

/**
 * All the JDBC command we used.
 */
public class JDBC {
    /*
    Here we need to add the JDBC commands to Add , Edit and Delete from Database
     */

    //maybe need to for later ... just keep it for now
    //PreparedStatement
    //Connection con = null;
    //PreparedStatement preparedStatement = null;
    //ResultSet resultSet = null;

    /**
     * All the JDBC command related to login by searching for match between javafx textFields and the db.
     */

    public String loginTSQL(){

        //SQL_Query to read the user name and the password from the database .
        String sql = "SELECT * from tbl_Users where fld_UserName = ? and fld_Password = ?";
        return sql;
    }



    /**
     * All the JDBC command to related to the users.
     */

    public String CheckUserTSQL(){

        //SQL_Query to......
        String sql = "SELECT* from tbl_Users where fld_UserName = ?";
        return sql;
    }

    public String ViewUserTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Users ";
        return sql;
    }

    public String EditUserTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteUserTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }




    /**
     * All the JDBC command to related to the Educations.
     */
    public String CreateEducationTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String ViewEducationTSQL(){

        //SQL_Query to......
        String sql = "SELECT * from tbl_Educations";
        return sql;
    }

    public String EditEducationTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteEducationTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }




    /**
     * All the JDBC command to related to the Companies.
     */
    public String CreateCompanyTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String ViewCompanyTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String EditCompanyTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteCompanyTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }



    /**
     * All the JDBC command to related to the Employees.
     */
    public String CreateEmployeeTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String ViewEmployeeTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String EditEmployeeTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteEmployeeTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }




    /**
     * All the JDBC command to related to the Assign a User.
     */
    public String CreateAssignTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String ViewAssignTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String EditAssignTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteAssignTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }




    /**
     * All the JDBC command to related to the EducationPlane .
     */
    public String CreateEducationPlaneTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String ViewEducationPlaneTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String EditEducationPlaneTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }

    public String DeleteEducationPlaneTSQL(){

        //SQL_Query to......
        String sql = "";
        return sql;
    }








}
