package Technical;

import Foundation.DB;
import java.sql.Connection;


public class StoredProcedures {
    //open a connection to MySQL server by creating a new Connection object
    static Connection con = DB.connect();


    /**
     * Stored procedure to check login.
     */
    public static String checkLogin(){
        String sql = "{ call checkLogin(? , ?) }";
        return sql;
    }

    /**
     * Stored procedure to view Specific User.
     */
    public static String ViewSpecificUser(){
        String sql = "{ call ViewSpecificUser(?) }";
        return sql;
    }

    /**
     * Stored procedure to create a new Education.
     */
    public static String createNewEdu() {
        String sql = "{ call createNewEdu(? , ? , ? , ? , ? , ? ) }";
        return sql;

    }
}
