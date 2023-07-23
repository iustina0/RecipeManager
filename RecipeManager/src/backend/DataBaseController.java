package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    
    static public int getNextIdIngredients() throws SQLException {
        try (Connection connection = DataBaseManager.getConnection(); Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select max(id) from Ingredients")) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
    
    static public int getNextIdRecipes() throws SQLException {
        try (Connection connection = DataBaseManager.getConnection(); Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select max(id) from Recipes")) {
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
    public static List<Integer> getUserIngredientIds() throws SQLException {
        List<Integer> ingredientIds = new ArrayList<>();
        
        String query = "SELECT id_ingredient FROM UserIngredient WHERE id_user = ?";
        
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, User.id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int ingredientId = resultSet.getInt("id_ingredient");
                    ingredientIds.add(ingredientId);
                }
            }
        }
        return ingredientIds;
    }
    public static void addIngredient(String name, String category) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "INSERT INTO Ingredients (id, name, category) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, getNextIdIngredients());
                statement.setString(2, name);
                statement.setString(3, category);
                System.out.println("addmin : " +getNextIdIngredients() + "  " + name + "  "+ category);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void addRecipe(String name) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "INSERT INTO Recipes (id, name) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1,  getNextIdRecipes());
                statement.setString(2, name);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void addRecipeIngredient(int recipeId, int ingredientId, int quantity, String quantityUnit) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "INSERT INTO RecipeIngredient (id_recipe, id_ingredient, quantity, quantity_unit) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, recipeId);
                statement.setInt(2, ingredientId);
                statement.setInt(3, quantity);
                statement.setString(4, quantityUnit);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void addUserIngredient(int userId, int ingredientId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "INSERT INTO UserIngredient (id_user, id_ingredient) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, ingredientId);
                statement.executeUpdate();
            }
                catch  (SQLException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            connection.close();
        }
    }
    public static void deleteUserIngredientsByUserId(int userId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "DELETE FROM UserIngredient WHERE id_user = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void deleteUserIngredient(int userId, int ingredientId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "DELETE FROM UserIngredient WHERE id_user = ? AND id_ingredient = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, ingredientId);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void deleteRecipeIngredientsByRecipeId(int recipeId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "DELETE FROM RecipeIngredient WHERE id_recipe = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, recipeId);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static void deleteRecipeIngredient(int recipeId, int ingredientId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "DELETE FROM RecipeIngredient WHERE id_recipe = ? AND id_ingredient = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, recipeId);
                statement.setInt(2, ingredientId);
                statement.executeUpdate();
            }
            connection.close();
        }
    }
    public static boolean ingredientExists(String ingredientName) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM Ingredients WHERE name = ?";

        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ingredientName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    exists = (count > 0);
                }
            }
            connection.close();
        } catch (SQLException e) {
        }

        return exists;
    }
    public static boolean isUserIngredientExists(int userId, int ingredientId) throws SQLException {
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "SELECT COUNT(*) FROM UserIngredient WHERE id_user = ? AND id_ingredient = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, ingredientId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        connection.close();
                        return count > 0;
                    }
                }
                connection.close();
            }
        } 
        return false;
    }
    
    
}
