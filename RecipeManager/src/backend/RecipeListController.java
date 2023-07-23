/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import com.opencsv.CSVReader;
import frontendControllers.RecipePageController;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Iustina
 */
public class RecipeListController {
    static int currentGroupNo;
    public static List<Group> recipeGroupList= new ArrayList<>();
    
    public static void regenarate() {
        double y=50;
        recipeGroupList.clear(); 
        currentGroupNo = 0;
        for (int id : User.recipes) {
            if(recipeGroupList.size()%5==0){
                 y=50;
            }
            Group group = new Group();
            group = createGroup(id, 50.0, y, getRecipeNameFromId(id), "" , getFirstImagePath(id));
            recipeGroupList.add(group);
            y+=215;
        }
    }
    
    public static String getFirstImagePath(int recipeId) {
        String imagePath = "";
        try (Connection connection = DataBaseManager.getConnection()) {
            String query = "SELECT filePath FROM recipeImages WHERE recipe_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, recipeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        imagePath = resultSet.getString("filePath");
                    }
                }
            }
        } catch (SQLException e) {
        }

        return imagePath;
    }
    
    public static String getRecipeNameFromId(int recipeId) {
        String recipeName = "";

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
                    recipeName = nextLine[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
        }

        return recipeName;
    }
    
    
    
     public static Group createGroup(int id, double layoutX, double layoutY, String title, String infoText, String imageUrl) {
         int desiredWidth=200;
         int desiredHeight=130;
        Group group = new Group();
        group.setId(Integer.toString(id));
        
        Rectangle rectangle = new Rectangle();
        rectangle.setArcWidth(10.0); 
        rectangle.setArcHeight(10.0); 
        rectangle.setFill(Color.web("#eeeeee"));
        rectangle.setHeight(156.0);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setStrokeWidth(0.0);
        rectangle.setEffect(new DropShadow(10, Color.GRAY)); 
        rectangle.setWidth(776.0);
        ImageView imageView = new ImageView();
        if(!imageUrl.isEmpty()){
            Image newImage = new Image(imageUrl);
            imageView.setImage(newImage);
        }
        else{
            Image newImage = new Image("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Papirus-Team-Papirus-Status-Image-missing.512.png");
            imageView.setImage(newImage);
        }
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);
        imageView.setLayoutX(79.0);
        imageView.setLayoutY(10);
        

        Text text = new Text();
        text.setFill(Color.web("#404040"));
        text.setLayoutX(306.0);
        text.setLayoutY(72.0);
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText(infoText);
        text.setWrappingWidth(174.7607421875);
        text.setFont(Font.font("Calibri", 14.0));

        ImageView imageView2 = new ImageView();
        imageView2.setFitHeight(150.0);
        imageView2.setFitWidth(200.0);
        imageView2.setLayoutX(79.0);
        imageView2.setLayoutY(-8);
        imageView2.setPreserveRatio(true);

        Text text2 = new Text();
        text2.setFill(Color.web("#404040"));
        text2.setLayoutX(306.0);
        text2.setLayoutY(46.0);
        text2.setStrokeType(StrokeType.OUTSIDE);
        text2.setStrokeWidth(0.0);
        text2.setText(title);
        text2.setFont(Font.font("Calibri", 25.0));

        group.getChildren().addAll(rectangle, imageView, text, imageView2, text2);
        group.setLayoutX(layoutX);
        group.setLayoutY(layoutY);

       return group;
    }

}
