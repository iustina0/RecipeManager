package frontendControllers;


import backend.DataBaseController;
import static backend.User.logoutUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminIngredientsPageController  implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text addIngredientButton;

    @FXML
    private ChoiceBox<String> categoryFeild;

    @FXML
    private TextField ingredientNameFeild;

    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private Text signOutButton;

    @FXML
    void ingredientsAdminPage(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/ingredientsAdminPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void recipeAdminPage(MouseEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/recipesAdminPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signOutUser(MouseEvent event) throws IOException {
        logoutUser();
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/loginPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public boolean containsOnlyLetters(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String lettersRegex = "[a-zA-Z ]+";
        return str.matches(lettersRegex);
    }
    @FXML
    void submitIngredient(MouseEvent event) throws SQLException {
        if(containsOnlyLetters(ingredientNameFeild.getText())){
            if(!DataBaseController.ingredientExists(ingredientNameFeild.getText())){
                DataBaseController.addIngredient(ingredientNameFeild.getText(), categoryFeild.getValue());
            }
            else{
                System.out.println("ingredient name alredy in the data base");
            }
        }
        else{
            System.out.println("ingredient name not valid");
        }
        ingredientNameFeild.clear();
        categoryFeild.getSelectionModel().clearSelection();
    }

   @Override
    public void initialize(URL location, ResourceBundle resources) {
            start();
        
    }
    
    @FXML
    void start() {
        String[] categories= {"Essentials", "Fruits", "Berries", "Nuts & Seeds", "Dairy & Eggs", "Substitutes", "Grains & Cereals", "Desserts & Sweets"};
        categoryFeild.getItems().addAll(categories);
    }

}

