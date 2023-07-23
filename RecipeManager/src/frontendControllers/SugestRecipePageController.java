package frontendControllers;

import backend.DataBaseManager;
import static backend.User.logoutUser;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.TextFields;

public class SugestRecipePageController  implements Initializable {

    private File selectedFile;
    private boolean canAdd1;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label fileNameLabel;
    
    @FXML
    private Button submitRecipeImageButton;
    
    @FXML
    private AnchorPane flagPane;

    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private Button loadImageButton;

    @FXML
    private AnchorPane proposePane;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private TextField recipeNameFeild;

    @FXML
    private Text signOutButton;

    @FXML
    private AnchorPane submitPane;


    @FXML
    private Text sugestMenuButton;

    @FXML
    private ImageView validateImage;

    @FXML
    private ImageView validateNameImage;

    @FXML
    void ingredientsPage(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/ingredientsPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void loadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.setInitialDirectory(new File("C:\\Users\\Iustina\\Downloads"));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedFile = fileChooser.showOpenDialog(loadImageButton.getScene().getWindow());
        if (selectedFile != null) {
            fileNameLabel.setText(selectedFile.getName());
            String newImageURL = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Accept-icon.png";
            Image newImage = new Image(newImageURL);
            validateImage.setImage(newImage);
        }
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
    void signOutUser(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/userPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void submitRecipeImage(ActionEvent event) {
        if (selectedFile != null && canAdd1) {
            String saveDirectory = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images";
            File saveFile = new File(saveDirectory, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String recipeName = recipeNameFeild.getText();
                int recipeId = getRecipeIdFromName(recipeName);
                String filePath = saveFile.getAbsolutePath();
                insertIntoRecipeImagesTable(recipeId, filePath);
            } catch (IOException e) {
            }
        }
    }
    
   private int getRecipeIdFromName(String recipeName) {
        int recipeId = -1;

        try (CSVReader reader = new CSVReader(new FileReader("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\CulinaryDB\\01_Recipe_Details.csv"))) {
            String[] nextLine;
            boolean headerSkipped = false;

            while ((nextLine = reader.readNext()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String name = nextLine[1].trim(); 

                if (name.equalsIgnoreCase(recipeName.trim())) {
                    recipeId = Integer.parseInt(nextLine[0]); 
                    break;
                }
            }
        } catch (IOException e) {
        }
        return recipeId;
   }

    private void insertIntoRecipeImagesTable(int recipeId, String filePath) {
        try (Connection connection =DataBaseManager.getConnection()) {
            String sql = "INSERT INTO recipeImages (recipe_id, filePath) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, recipeId);
                statement.setString(2, filePath);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
    }
    }

    @FXML
    void sugestPage(MouseEvent event) {

  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }
    
    @FXML
    private void start() {
        String newImageURL = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Custom-Icon-Design-Flatastic-1-Delete-1.512.png";
       Image newImage = new Image(newImageURL);
        validateNameImage.setImage(newImage);
        validateImage.setImage(newImage); 
        
        submitRecipeImageButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
        submitRecipeImageButton.setOnMouseEntered(event -> {
                submitRecipeImageButton.setStyle("-fx-background-color: #5B3A8A; -fx-text-fill: white;");
            });

         submitRecipeImageButton.setOnMouseExited(event -> {
                submitRecipeImageButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
            });
         
        loadImageButton.setStyle("-fx-background-color: #47bc57; -fx-text-fill: white;");
        loadImageButton.setOnMouseEntered(event -> {
                loadImageButton.setStyle("-fx-background-color: #368d42; -fx-text-fill: white;");
            });

         loadImageButton.setOnMouseExited(event -> {
                loadImageButton.setStyle("-fx-background-color: #47bc57; -fx-text-fill: white;");
            });
        canAdd1=false;
        recipeNameFeild.setOnAction(event -> {
                checkEnterdName();
            });

            recipeNameFeild.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    checkEnterdName();
                }
            });
    }
    void checkEnterdName(){
        List<String> recipeNames = getRecipeNamesFromCSV();
        if(recipeNames.contains(recipeNameFeild.getText())){
            String newImageURL = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\Accept-icon.png";
            Image newImage = new Image(newImageURL);
            validateNameImage.setImage(newImage);
            canAdd1=true;
        }
    }

    private List<String> getRecipeNamesFromCSV() {
        List<String> recipeNames = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\CulinaryDB\\01_Recipe_Details.csv"))) {
            String[] nextLine;
            boolean headerSkipped = false;

            while ((nextLine = reader.readNext()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String name = nextLine[1].trim();
                recipeNames.add(name);
            }
        } catch (IOException e) {
        }

        return recipeNames;
    }


}