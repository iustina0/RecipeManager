
package frontendControllers;

import static backend.User.logoutUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminRecipePageController   implements Initializable  {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text addRecipeButton;

    @FXML
    private TextField ingredientNameFeild;

    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private Text quantatyFeild;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private TextField recipeNameFeild;

    @FXML
    private TextField recipeNameFeild11;

    @FXML
    private Text signOutButton;

    @FXML
    private ChoiceBox<String> unitFeild;

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
    
    public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String numericRegex = "-?\\d+(\\.\\d+)?";
        return str.matches(numericRegex);
    }

    @FXML
    void submitRecipe(MouseEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            start();
        
    }
    
    @FXML
    void start() {
        String[] units= {"cup", "teaspoon", "tablespoon", "ounce", "pound", "gram", "kilogram", "milliliter", "liter", "fluid ounce", "pint", "quart", "gallon"};
        unitFeild.getItems().addAll(units);
    }

}