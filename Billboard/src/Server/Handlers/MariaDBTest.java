package Server.Handlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import Tools.HashCredentials;
import Tools.Log;
import Tools.PropertyReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import static java.lang.System.exit;
import static java.lang.System.out;
/*************************************************** This is the test class for the MariaDB****************************************************************
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *************************************************************************************************************************************************************/
public class MariaDBTest {
    byte[] testBytes = new byte[]{0, 1, 2};
    MariaDB undertest;
    @BeforeEach
    void NewMariaDB() {
        undertest = new MariaDB();
    }
    /**
     *
     */
    @Test
    public void AddTester() throws SQLException {
        MariaDB.Billboards.addBillboard("name", "msg", "info", "picURL", testBytes, "msgColour", "backColour", "infoColour");{
        }
    }
}