
package frontendControllers;

import backend.DataBaseManager;
import backend.Recipe;
import backend.User;
import static backend.User.logoutUser;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

public class UserPageController  implements Initializable  {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button deleteButton;

    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private Button loadButton;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private Button signOutButton1;

    @FXML
    private Text sugestMenuButton;

    @FXML
    private ImageView userImage;

    @FXML
    private Text userNameText;

    @FXML
    void deleteUser(ActionEvent event) throws IOException {
       int userIdToDelete = User.id; 
        String deleteFromUserIngredientQuery = "DELETE FROM UserIngredient WHERE id_user = ?";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteFromUserIngredientQuery)) {
            statement.setInt(1, userIdToDelete);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String deleteFromUsersQuery = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteFromUsersQuery)) {
            statement.setInt(1, userIdToDelete);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logoutUser();
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/loginPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            
    }

    @FXML
    void ingredientsPage(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/ingredientsPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loadData(ActionEvent event) {
        List<Recipe> recipeList=new ArrayList<>();
        for(int i : User.recipes){
            recipeList.add(new Recipe(i));
        }
         String HTML_TEMPLATE_PATH = "D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\src\\graphicStyle\\template.html";
         String OUTPUT_FILE_PATH = "output.html";

        try (BufferedReader reader = new BufferedReader(new FileReader(HTML_TEMPLATE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {

            StringBuilder templateBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                templateBuilder.append(line);
            }
            String htmlTemplate = templateBuilder.toString();

            StringBuilder reportBuilder = new StringBuilder();
            for (Recipe recipe : recipeList) {
                String modifiedTemplate = htmlTemplate
                        .replace("{{RECIPE_NAME}}", recipe.getName())
                        .replace("{{RECIPE_IMAGE_URL}}", recipe.getImageUrl())
                        .replace("{{RECIPE_SOURCE}}", recipe.getSource())
                        .replace("{{RECIPE_SOURCE_WEBISTE}}", recipe.getUrl())
                        .replace("{{RECIPE_CUISINE}}", recipe.getCuisine());

                reportBuilder.append(modifiedTemplate);
            }

            writer.write(reportBuilder.toString());

            System.out.println("HTML report generated successfully.");
            File htmlFile = new File(OUTPUT_FILE_PATH);
            Desktop.getDesktop().browse(htmlFile.toURI());

        } catch (IOException e) {
            e.printStackTrace();
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
    void signOutUser(ActionEvent event) throws IOException {
        logoutUser();
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/loginPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void sugestPage(MouseEvent event) throws IOException {
         root=FXMLLoader.load(getClass().getResource("../graphicStyle/sugestRecipePage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    private void start() {
        userNameText.setText(User.username);
        Image image=new Image("D:\\an2sem2\\PA-java\\work-java\\proiect_final\\RecipeManager\\resources\\images\\fg-avatar-anonymous-user-retina.png");
        userImage.setImage(image);
        deleteButton.setStyle("-fx-background-color: #cc0000; -fx-text-fill: white;");
        deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStyle("-fx-background-color: #b70000; -fx-text-fill: white;");
            });

         deleteButton.setOnMouseExited(event -> {
                deleteButton.setStyle("-fx-background-color: #cc0000; -fx-text-fill: white;");
            });
         
         
         loadButton.setStyle("-fx-background-color: #25821A; -fx-text-fill: white;");
        loadButton.setOnMouseEntered(event -> {
                loadButton.setStyle("-fx-background-color: #1E6A15; -fx-text-fill: white;");
            });

         loadButton.setOnMouseExited(event -> {
                loadButton.setStyle("-fx-background-color: #25821A; -fx-text-fill: white;");
            });
         
         
         signOutButton1.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
        signOutButton1.setOnMouseEntered(event -> {
                signOutButton1.setStyle("-fx-background-color: #5B3A8A; -fx-text-fill: white;");
            });

         signOutButton1.setOnMouseExited(event -> {
                signOutButton1.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
            });
    }

}
