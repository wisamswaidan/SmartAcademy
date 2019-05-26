import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JustTest {
    public static void main(String[] argv) throws SQLException {

        Connection con = DB.connect();
        PreparedStatement stmt =con.prepareStatement("SELECT * from tblUser");
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1));
        }
        con.close();
    }



}
