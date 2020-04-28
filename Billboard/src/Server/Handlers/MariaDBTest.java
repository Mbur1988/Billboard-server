package Server.Handlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
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
        undertest.Billboards.addBillboard("name", "msg", "info", "picURL", testBytes, "msgColour", "backColour", "infoColour"){
        }
    }
}