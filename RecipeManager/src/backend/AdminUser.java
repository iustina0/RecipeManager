
package backend;

import java.sql.*;

/**
 *
 * @author Iustina
 */
public class AdminUser {
    static private boolean adminUserLoged;
    
    public void setAdminUserLoged(boolean adminStatus){
        AdminUser.adminUserLoged=adminStatus;
    }
    public boolean isAdminUserLoged(){
        return AdminUser.adminUserLoged;
    }
    
    
    
    
    static public void addUser(String username, String password, String email) {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "INSERT INTO Users (id, username, email, password_hash) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, User.getNextIdUsers());
                statement.setString(2, username);
                statement.setString(3, email);
                statement.setString(4, User.hashPassword(password));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
        System.out.println(e);
        }
    }
}
