
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Iustina
 */
public class DataBaseManager {
   private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
   private static final String DB_USERNAME = "admin";
   private static final String DB_PASSWORD = "ADMIN";
   
   public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
}
