
package backend;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author Iustina
 */
public class RatingController {
    
    
      public static void updateRating(int recipeId, double ratingValue) {
        String csvFilePath = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\CulinaryDB\\01_Recipe_Details.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                String[] nextLine;
                boolean headerSkipped = false;
                while ((nextLine = reader.readNext()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    int id = Integer.parseInt(nextLine[0]);

                    if (id == recipeId) {
                        String ratingStr = nextLine[4].trim();
                        double existingRating = ratingStr.isEmpty() ? 0.0 : Double.parseDouble(ratingStr);
                        double averageRating = (existingRating + ratingValue) / 2;
                        nextLine[4] = String.valueOf(averageRating);

                        break;
                    }
                }

                // Write the updated data back to the CSV file
                try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                    writer.writeAll(reader.readAll());
                }
                updateRatingInDatabase(recipeId, (int) ratingValue);
        } catch (IOException e) {
        }
    }

  public static void updateRatingInDatabase(int recipeId, int rating) {
        try (Connection connection = DataBaseManager.getConnection()) {
            String sql = "MERGE INTO UserRecipeRating r " +
                    "USING (SELECT ? AS user_id, ? AS recipe_id, ? AS rating FROM dual) d " +
                    "ON (r.user_id = d.user_id AND r.recipe_id = d.recipe_id) " +
                    "WHEN MATCHED THEN UPDATE SET r.rating = d.rating " +
                    "WHEN NOT MATCHED THEN INSERT (user_id, recipe_id, rating) VALUES (d.user_id, d.recipe_id, d.rating)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, User.id);
                statement.setInt(2, recipeId);
                statement.setInt(3, rating);
                statement.executeUpdate();
            }
            
            System.out.println("Rating updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors that may occur during the database interaction
        }
    }
    
  public static void addOrUpdateRating(int recipeId, int ratingValue) {
        try (Connection conn = DataBaseManager.getConnection()) {
            String selectQuery = "SELECT rating FROM RecipeRating WHERE recipe_id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, recipeId);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                double existingRating = resultSet.getDouble("rating");
                double averageRating = (existingRating + ratingValue) / 2;

                String updateQuery = "UPDATE RecipeRating SET rating = ? WHERE recipe_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDouble(1, averageRating);
                updateStmt.setInt(2, recipeId);
                updateStmt.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO RecipeRating (recipe_id, rating) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, recipeId);
                insertStmt.setInt(2, ratingValue);
                insertStmt.executeUpdate();
            }

            System.out.println("Rating added or updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public static String getRatingAsString(int recipeId) {
        String rating = "";

        try (Connection conn = DataBaseManager.getConnection()) {
            String selectQuery = "SELECT rating FROM RecipeRating WHERE recipe_id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, recipeId);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                double ratingValue = resultSet.getDouble("rating");
                rating = String.valueOf(ratingValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rating;
    }

}
