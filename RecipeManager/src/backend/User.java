
package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iustina
 */
public class User {
    public static String username;
    public static String email;
    public static String passwordHash;
    public static int id;
    public static List<Integer> recipes=new ArrayList<>();
    
    
    
     public User() {
        
    }
     
     
     
     static public int getNextIdUsers() throws SQLException {
        Connection connection = DataBaseManager.getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select max(id) from Users")) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
     
     static public void logoutUser(){
         User.username=null;
         User.email=null;
         User.passwordHash=null;
         User.id=-1;
     }
     
     
    static public int validateLogin(String username, String password) throws SQLException {
      try (Connection connection = DataBaseManager.getConnection()) {
        if (!isUsernameExists(connection, username) && !isEmailExists(connection, username)) {
            System.out.println("username or email not correct");
            return -1;
        } else {
            String query;
            String column;

            if (isEmailExists(connection, username)) {
                User.email = username;
                query = "SELECT id, password_hash, username FROM Users WHERE email = ?";
                column = "username";
            } else {
                User.username = username;
                query = "SELECT id, password_hash, email FROM Users WHERE username = ?";
                column = "email";
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("id");
                        String storedPasswordHash = resultSet.getString("password_hash");
                        if (validatePassword(password, storedPasswordHash) == 1) {
                            User.id = userId;
                            User.email = resultSet.getString(column);
                            User.passwordHash = hashPassword(password);

                            String query1 = "SELECT username FROM Users WHERE " + column + " = ?";
                            try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
                                statement1.setString(1, username);
                                try (ResultSet resultSet1 = statement1.executeQuery()) {
                                    if (resultSet1.next()) {
                                        User.username = resultSet1.getString("username");
                                    }
                                }
                            } catch (SQLException e) {
                            }

                            return 1;
                        } else {
                            System.out.println("Password not correct");
                            return 0;
                        }
                    }
                }
            } catch (SQLException e) {
            }
        }
    } catch (SQLException e) {
    }

    return -1;
    }
     
      static public int validatePassword(String enteredPassword, String storedPasswordHash) {
            String enteredPasswordHash = hashPassword(enteredPassword);
            if(enteredPasswordHash.equals(storedPasswordHash)){
                return 1;
            }
            return 0;
        }

     
     static public int signUp(String username, String password, String email) {
        try (Connection connection = DataBaseManager.getConnection()) {
            if (isEmailExists(connection, email)) {
                System.out.println("Email or username already exists.");
                return -1;
            }
             if (isUsernameExists(connection, username)) {
                System.out.println("Email or username already exists.");
                return -1;
            }

            String query = "INSERT INTO Users (id, username, email, password_hash) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int nextId=User.getNextIdUsers();
                statement.setInt(1, nextId);
                statement.setString(2, username);
                statement.setString(3, email);
                statement.setString(4, User.hashPassword(password));
                statement.executeUpdate();
                User.id=nextId;
                User.username=username;
                User.email=email;
                User.passwordHash=User.hashPassword(password);
                return 1;
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String constraintName = e.getMessage();
            System.out.println("Constraint violation: " + constraintName);

            if (constraintName.equals("unique_user_name")) {
                System.out.println("Username already exists.");
            } else if (constraintName.equals("unique_email")) {
                System.out.println("Email already exists.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    static private boolean isEmailExists(Connection connection, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    static private boolean isUsernameExists(Connection connection, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }


    static public String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hashedBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
            }
            return null;
    }

    
}
