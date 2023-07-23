package frontendControllers;
import backend.RecipeListController;
import backend.DataBaseController;
import backend.DataBaseManager;
import backend.RatingController;
import backend.User;
import static backend.User.id;
import static backend.User.logoutUser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class RecipePageController  implements Initializable  {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int pageNo=0; 
    private int currentPage;
    public static List<Group> currentlyShown;

    @FXML
    private Pane bigPane;
    
    @FXML
    private AnchorPane recipePane;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private ImageView outLinkButton;

    @FXML
    private ImageView outLinkButton1;

    @FXML
    private ImageView outLinkButton2;

    @FXML
    private ImageView outLinkButton3;

    @FXML
    private ImageView outLinkButton4;

    @FXML
    private Button previousPageButton;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private Text signOutButton;

    @FXML
    private Text sugestMenuButton;
    
    @FXML
    private Text currentPageText;

    @FXML
    void ingredientsPage(MouseEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/ingredientsPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void nextPage(ActionEvent event) {
        if(RecipeListController.recipeGroupList.size()>currentPage*5-1){
            currentPage++;
            currentPageText.setText(getCurrentPageAsString());
            printPage();
        }
    }

    @FXML
    void previousPage(ActionEvent event) {
        if(currentPage>1){
            currentPage--;
            currentPageText.setText(getCurrentPageAsString());
            printPage();
        }
    }
    
    public int getCurrentPage(){
        return currentPage;
    }
    public String getCurrentPageAsString() {
        return String.valueOf(currentPage);
    }

    @FXML
    void recipePage(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/recipesPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signOutUser(MouseEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/userPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void sugestPage(MouseEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/sugestRecipePage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            start();
        } catch (SQLException ex) {
            Logger.getLogger(RecipePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecipePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void start() throws SQLException, FileNotFoundException, IOException {
        scrollPane.setVvalue(0);
        scrollPane.layout();
        User.recipes.clear();
        currentPage=1;
        currentPageText.setText(getCurrentPageAsString());
        int c=0;
        List<Integer> userIngredientIds = DataBaseController.getUserIngredientIds();
        String filePath = "D:/an2sem2/PA-java/work-java/proiect_final/RecipeManager/src/CulinaryDB/04_Recipe-Ingredients_Aliases.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             CSVReader csvReader = new CSVReader(reader)) {
            String[] nextLine;

            int currentRecipeId = -1;
            List<Integer> recipeIngredientIds = new ArrayList<>();
            
            for(int i=0; i<2609; i++){
                nextLine = csvReader.readNext();
            }
            

            while ((nextLine = csvReader.readNext()) != null && c<300000) {
                c++;
                int recipeId;
                try {
                    recipeId = Integer.parseInt(nextLine[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing recipe ID: " + nextLine[0]);
                    continue;  
                }

                int ingredientId;
                try {
                    ingredientId = Integer.parseInt(nextLine[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing ingredient ID: " + nextLine[3]);
                    continue;  
                }

                if (recipeId != currentRecipeId) {
                    if (currentRecipeId != -1 && userIngredientIds.containsAll(recipeIngredientIds) && recipeId>2600) {
                        User. recipes.add(currentRecipeId);
                    }

                    currentRecipeId = recipeId;
                    recipeIngredientIds.clear();
                }

                recipeIngredientIds.add(ingredientId);
            }
            if (currentRecipeId != -1 && userIngredientIds.containsAll(recipeIngredientIds)) {
                User. recipes.add(currentRecipeId);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        System.out.println("total retete : " +  User. recipes.size());
       if (User.recipes.isEmpty()) {
        } else {
            RecipeListController.regenarate();
            for(Group i : RecipeListController.recipeGroupList){
                i.setOnMouseClicked(event -> {
                    String groupId = ((Group) event.getSource()).getId();
                    try {
                        showRecipe(Integer.parseInt(groupId));
                    } catch (IOException ex) {
                        Logger.getLogger(RecipePageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
                    printPage();
            if (User.recipes.size() <= 5) {
                if (User.recipes.size() <= 2) {
                    recipePane.setPrefHeight(540);
                } else {
                    recipePane.setPrefHeight(500 + 200 * (User.recipes.size() - 2));
                }
         } 
       }
    }

    private void printPage() {
       List<Group> groupsToRemove = new ArrayList<>();

        for (Node child : recipePane.getChildren()) {
            if (child instanceof Group && (child.getId() == null || (!child.getId().equals("otherGroup1") && !child.getId().equals("otherGroup2")))) {
                groupsToRemove.add((Group) child);
            }
        }

        recipePane.getChildren().removeAll(groupsToRemove);

        int startIndex = (currentPage - 1) * 5;
        int endIndex = Math.min(startIndex + 4, RecipeListController.recipeGroupList.size() - 1);

        for (int i = startIndex; i <= endIndex; i++) {
            Group group = RecipeListController.recipeGroupList.get(i);
            recipePane.getChildren().add(group);
        }
        scrollPane.setVvalue(0);
    }

       Rectangle rectangle= new Rectangle();
       Group groups = new Group();
    @FXML
    public  void showRecipe(int id) throws IOException {
        rectangle.setStroke(TRANSPARENT);
        rectangle.setHeight(553.0);
        rectangle.setWidth(900);
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setFill(Color.web("#D5B7FE"));
        rectangle.setLayoutX(0);
        rectangle.setLayoutY(67);
        bigPane.getChildren().add(rectangle);
        
        
        
        
        TextFlow textFlowIngredients = new TextFlow();
        textFlowIngredients.setLayoutX(350.0);
        textFlowIngredients.setLayoutY(213.0);
        textFlowIngredients.setPrefHeight(253.0);
        textFlowIngredients.setPrefWidth(348.0);
         try (BufferedReader reader = new BufferedReader(new FileReader("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\CulinaryDB\\04_Recipe-Ingredients_Aliases.csv"));
             CSVReader csvReader = new CSVReader(reader)) {
             String[] nextLine;
             int c=0;
             while ((nextLine = csvReader.readNext()) != null && c<200000) {
                c++;
                
                String idRead = nextLine[0];
                String ingredientName = nextLine[1];
                if (idRead.equals(Integer.toString(id))) {
                    Text ingredientText = new Text(ingredientName + "\n");
                    ingredientText.setFill(Color.web("#260032"));
                    ingredientText.setFont(new Font("Calibri", 22.0));
                    textFlowIngredients.getChildren().add(ingredientText);
                } 
             }
         } catch (FileNotFoundException ex) {
            Logger.getLogger(RecipePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        textFlowIngredients.setStyle("-fx-text-fill: WHITE;");

        String imageUrl=RecipeListController.getFirstImagePath(id);
        ImageView recipeImage = new ImageView();
        if(!imageUrl.isEmpty()){
            Image newImage = new Image(imageUrl);
            recipeImage.setImage(newImage);
        }
        else{
            Image newImage = new Image("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Papirus-Team-Papirus-Status-Image-missing.512.png");
            recipeImage.setImage(newImage);
        }
        recipeImage.setFitHeight(222.0);
        recipeImage.setFitWidth(269.0);
        recipeImage.setLayoutX(100.0);
        recipeImage.setLayoutY(179.0);
        recipeImage.setPickOnBounds(true);
        recipeImage.setPreserveRatio(true);
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Calibri", 22.0));
        backButton.setLayoutX(50.0);
        backButton.setLayoutY(100.0);
        backButton.setTextFill(javafx.scene.paint.Color.WHITE);
        backButton.setOnAction(event -> {
             groups.getChildren().clear();
            reprintPage();
                });
         backButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
        backButton.setOnMouseEntered(event -> {
                backButton.setStyle("-fx-background-color: #5B3A8A; -fx-text-fill: white;");
            });

         backButton.setOnMouseExited(event -> {
                backButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
            });
        
        Text cuisineText = new Text("Source: " + getSourceFromId(id));
        cuisineText.setFill(Color.web("#260032"));
        cuisineText.setLayoutX(130.0);
        cuisineText.setLayoutY(425.5);
        cuisineText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        cuisineText.setStrokeWidth(0.0);
        cuisineText.setFont(new Font("Calibri", 22.0));
        
        Text sourceText = new Text("Cuisine: " + getCuisineFromId(id));
        sourceText.setOnMouseClicked(event -> {
            String url;
           switch (getCuisineFromId(id)) {
              case "EPICURIOUS" -> url = "https://www.epicurious.com/";
              case "TARLA_DALAL" -> url = "https://www.tarladalal.com/";
              case "FOOD_NETWORK" -> url = "https://foodnetwork.co.uk/";
              case "ALLRECIPES" -> url = "https://www.allrecipes.com/";
              default -> {
                  url = "https://www.example.com";
                  System.out.println("Invalid source selected");
                }
          }
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sourceText.setFill(Color.web("#260032"));
        sourceText.setLayoutX(130.0);
        sourceText.setLayoutY(465.5);
        sourceText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        sourceText.setStrokeWidth(0.0);
        sourceText.setFont(new Font("Calibri", 20.0));
        
        Text titleText = new Text(RecipeListController.getRecipeNameFromId(id));
        titleText.setFill(Color.web("#260032"));
        titleText.setLayoutX(450.0);
        titleText.setLayoutY(146.1015625);
        titleText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        titleText.setStrokeWidth(0.0);
        titleText.setFont(new Font(24.0));
        
        Text ratingtext = new Text("Rating: " + RatingController.getRatingAsString(id));
        ratingtext.setFill(Color.web("#260032"));
        ratingtext.setLayoutX(357);
        ratingtext.setLayoutY(193);
        ratingtext.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        ratingtext.setStrokeWidth(0.0);
        ratingtext.setFont(new Font(16.0));
        
        Group group1 = new Group();

        Rectangle rectangle = new Rectangle(-4, -111, 171, 36);
        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);
        rectangle.setFill(Color.web("#764bb2"));
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setStrokeType(StrokeType.INSIDE);
        Polygon[] polygons = new Polygon[6];
        
        polygons[1] = new Polygon(
            -59, -114, -20.5, -81.66829681396484, -35, -34.470481872558594,
            10.5, -62.168296813964844, 58, -34.47047424316406, 43.5,
            -81.66829681396484, 82, -114, 29, -114, 10.5, -164.19293212890625,
            -8.5, -114
        );
        polygons[1].setLayoutX(6.4000244140625);
        polygons[1].setLayoutY(5.696044921875);
        polygons[1].setScaleX(0.2);
        polygons[1].setScaleY(0.2);
        polygons[1].setFill(Color.WHITE);
        polygons[1].setStroke(Color.TRANSPARENT);
        polygons[1].setStrokeType(StrokeType.INSIDE);

       
        polygons[2] = new Polygon(
            -59, -114, -20.5, -81.66829681396484, -35, -34.470481872558594,
            10.5, -62.168296813964844, 58, -34.47047424316406, 43.5,
            -81.66829681396484, 82, -114, 29, -114, 10.5, -164.19293212890625,
            -8.5, -114
        );
        polygons[2].setLayoutX(37.4000244140625);
        polygons[2].setLayoutY(5.696044921875);
        polygons[2].setScaleX(0.2);
        polygons[2].setScaleY(0.2);
        polygons[2].setFill(Color.WHITE);
        polygons[2].setStroke(Color.TRANSPARENT);
        polygons[2].setStrokeType(StrokeType.INSIDE);

       

        polygons[3] = new Polygon(
            -59, -114, -20.5, -81.66829681396484, -35, -34.470481872558594,
            10.5, -62.168296813964844, 58, -34.47047424316406, 43.5,
            -81.66829681396484, 82, -114, 29, -114, 10.5, -164.19293212890625,
            -8.5, -114
        );
        polygons[3].setLayoutX(69.4000244140625);
        polygons[3].setLayoutY(5.696044921875);
        polygons[3].setScaleX(0.2);
        polygons[3].setScaleY(0.2);
        polygons[3].setFill(Color.WHITE);
        polygons[3].setStroke(Color.TRANSPARENT);
        polygons[3].setStrokeType(StrokeType.INSIDE);

        
        polygons[4] = new Polygon(
            -59, -114, -20.5, -81.66829681396484, -35, -34.470481872558594,
            10.5, -62.168296813964844, 58, -34.47047424316406, 43.5,
            -81.66829681396484, 82, -114, 29, -114, 10.5, -164.19293212890625,
            -8.5, -114
        );
        polygons[4].setLayoutX(101.4000244140625);
        polygons[4].setLayoutY(5.696044921875);
        polygons[4].setScaleX(0.2);
        polygons[4].setScaleY(0.2);
        polygons[4].setFill(Color.WHITE);
        polygons[4].setStroke(Color.TRANSPARENT);
        polygons[4].setStrokeType(StrokeType.INSIDE);

        
        polygons[5] = new Polygon(
            -59, -114, -20.5, -81.66829681396484, -35, -34.470481872558594,
            10.5, -62.168296813964844, 58, -34.47047424316406, 43.5,
            -81.66829681396484, 82, -114, 29, -114, 10.5, -164.19293212890625,
            -8.5, -114
        );
         polygons[5].setLayoutX(137.4000244140625);
         polygons[5].setLayoutY(5.696044921875);
         polygons[5].setScaleX(0.2);
         polygons[5].setScaleY(0.2);
         polygons[5].setFill(Color.WHITE);
         polygons[5].setStroke(Color.TRANSPARENT);
         polygons[5].setStrokeType(StrokeType.INSIDE);

         if(findUserRating(id)!=0){
             for(int i=1; i <= findUserRating(id); i++){
                 polygons[i].setFill(Color.web("#ffef00"));
             }
         }
         else{
            polygons[1].setOnMouseEntered(event -> polygons[1].setFill(Color.web("#ffef00")));
            polygons[1].setOnMouseExited(event -> polygons[1].setFill(Color.WHITE));
            polygons[1].setOnMouseClicked(event -> {
                RatingController.updateRatingInDatabase(id, 1);
                RatingController.addOrUpdateRating(id, 1);
                ratingtext.setText("Rating: " + RatingController.getRatingAsString(id));
                for(int i = 1; i<=5;i++){
                   polygons[i].setOnMouseExited(null);
                   polygons[i].setOnMouseEntered(null);
                   polygons[i].setOnMouseClicked(null);
                }
            }); 

              polygons[2].setOnMouseEntered(event -> { 
                polygons[2].setFill(Color.web("#ffef00"));
                polygons[1].setFill(Color.web("#ffef00"));
            });
            polygons[2].setOnMouseExited(event ->{ 
                polygons[2].setFill(Color.WHITE);
                polygons[1].setFill(Color.WHITE);
            });
            polygons[2].setOnMouseClicked(event ->  {
                RatingController.updateRatingInDatabase(id, 2);
                RatingController.addOrUpdateRating(id, 2);
                ratingtext.setText("Rating: " + RatingController.getRatingAsString(id));
                for(int i = 1; i<=5;i++){
                   polygons[i].setOnMouseExited(null);
                   polygons[i].setOnMouseEntered(null);
                   polygons[i].setOnMouseClicked(null);
                }
            }); 

             polygons[3].setOnMouseEntered(event -> {
                polygons[3].setFill(Color.web("#ffef00"));
                polygons[2].setFill(Color.web("#ffef00"));
                polygons[1].setFill(Color.web("#ffef00"));
                }) ;
            polygons[3].setOnMouseExited(event ->{
                polygons[3].setFill(Color.WHITE);
                polygons[2].setFill(Color.WHITE);
                polygons[1].setFill(Color.WHITE);
            });
            polygons[3].setOnMouseClicked(event ->  {
                RatingController.updateRatingInDatabase(id, 3);
                RatingController.addOrUpdateRating(id, 3);
                ratingtext.setText("Rating: " + RatingController.getRatingAsString(id));
                for(int i = 1; i<=5;i++){
                   polygons[i].setOnMouseExited(null);
                   polygons[i].setOnMouseEntered(null);
                   polygons[i].setOnMouseClicked(null);
                }
            });

             polygons[4].setOnMouseEntered(event -> { 
                polygons[4].setFill(Color.web("#ffef00"));
                polygons[3].setFill(Color.web("#ffef00"));
                polygons[2].setFill(Color.web("#ffef00"));
                polygons[1].setFill(Color.web("#ffef00"));
            });
            polygons[4].setOnMouseExited(event -> { 
                polygons[4].setFill(Color.WHITE);
                polygons[3].setFill(Color.WHITE);
                polygons[2].setFill(Color.WHITE);
                polygons[1].setFill(Color.WHITE);
            });
            polygons[4].setOnMouseClicked(event ->  {
                RatingController.updateRatingInDatabase(id, 4);
                RatingController.addOrUpdateRating(id, 4);
                ratingtext.setText("Rating: " + RatingController.getRatingAsString(id));
                for(int i = 1; i<=5;i++){
                   polygons[i].setOnMouseExited(null);
                   polygons[i].setOnMouseEntered(null);
                   polygons[i].setOnMouseClicked(null);
                }
            }); 

             polygons[5].setOnMouseEntered(event ->{  polygons[5].setFill(Color.web("#ffef00"));
                polygons[4].setFill(Color.web("#ffef00"));
                polygons[3].setFill(Color.web("#ffef00"));
                polygons[2].setFill(Color.web("#ffef00"));
                polygons[1].setFill(Color.web("#ffef00"));
            });
             polygons[5].setOnMouseExited(event ->{  polygons[5].setFill(Color.WHITE);
                polygons[4].setFill(Color.WHITE);
                polygons[3].setFill(Color.WHITE);
                polygons[2].setFill(Color.WHITE);
                polygons[1].setFill(Color.WHITE);
            });
            polygons[5].setOnMouseClicked(event ->  {
                RatingController.updateRatingInDatabase(id, 5);
                RatingController.addOrUpdateRating(id, 5);
                ratingtext.setText("Rating: " + RatingController.getRatingAsString(id));
                for(int i = 1; i<=5;i++){
                   polygons[i].setOnMouseExited(null);
                   polygons[i].setOnMouseEntered(null);
                   polygons[i].setOnMouseClicked(null);
                }
            }); 

         }
        
        
        group1.getChildren().addAll(rectangle,  polygons[1],  polygons[2],  polygons[3],  polygons[4],  polygons[5]);
        group1.setLayoutX(671);
        group1.setLayoutY(614);

        groups.getChildren().addAll(backButton, textFlowIngredients, recipeImage, cuisineText, sourceText, titleText, group1, ratingtext);
        
        bigPane.getChildren().add(groups);
    }
    
    @FXML
    private void reprintPage() {
       bigPane.getChildren().remove(groups);
       bigPane.getChildren().remove(rectangle);
    }
    
    
    public static String getCuisineFromId(int recipeId) {
        String cuisine = "";

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
                    cuisine = nextLine[2].trim();
                    break;
                }
            }
        } catch (IOException e) {
        }

        return cuisine;
    }
    
    public static String getSourceFromId(int recipeId) {
        String source = "";

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
                    source = nextLine[3].trim();
                    break;
                }
            }
        } catch (IOException e) {
        }

        return source;
    }
    
    public static int findUserRating(int recipeId) {
        int userId = User.id; 
        int rating = 0;

        try (Connection connection = DataBaseManager.getConnection()) {
            String sql = "SELECT rating FROM UserRecipeRating WHERE user_id = ? AND recipe_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, recipeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        rating = resultSet.getInt("rating");
                    }
                }
            }
        } catch (SQLException e) {
        }

        return rating;
    }

}
