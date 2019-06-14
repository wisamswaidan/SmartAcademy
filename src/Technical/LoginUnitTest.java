package Technical;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginUnitTest {

    String userName = "hema";
    String passWord = "1234";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginTSQL() {

        boolean expected = true;
        boolean actual =  JDBC.loginTSQL(userName,passWord);
        assertEquals(expected,actual);
    }
}