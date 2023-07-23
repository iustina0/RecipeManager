
package frontendControllers;

import backend.DataBaseController;
import backend.DataBaseManager;
import backend.User;
import static backend.User.id;
import static backend.User.logoutUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IngredientPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    List<IngredientsToggleButtons> ToggleButtonsLists=new ArrayList<>();
    List<String> categories = Arrays.asList("Fruit", "Vegetable", "Dairy", "Nuts & Cereal", "Fungus & Legume", "Plant", "Meat", "Fish", "Seafood", "Hearbs & Spices", "Additive & Oil", "Beverage", "Other");
    @FXML
    private AnchorPane additivePane;

    @FXML
    private AnchorPane beveragePane;

    @FXML
    private AnchorPane dairyPane;

    @FXML
    private AnchorPane fishPane;

    @FXML
    private AnchorPane fruitPane;

    @FXML
    private AnchorPane fungusPane;

    @FXML
    private Text ingredientsMenuButton;

    @FXML
    private AnchorPane meatPane;

    @FXML
    private AnchorPane nutsPane;

    @FXML
    private AnchorPane otherPane;

    @FXML
    private AnchorPane plantPane;

    @FXML
    private Text recipeMenuButton;

    @FXML
    private AnchorPane seafoodPane;

    @FXML
    private Text signOutButton;

    @FXML
    private AnchorPane spicePane;

    @FXML
    private Text sugestMenuButton;

    @FXML
    private AnchorPane vegetablePane;


    @FXML
    void ingredientsPage(MouseEvent event)  throws IOException, SQLException{
        loadingChanges();
        root = FXMLLoader.load(getClass().getResource("../graphicStyle/ingredientsPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    void recipePage(MouseEvent event) throws IOException, SQLException {
        loadingChanges();
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/recipesPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signOutUser(MouseEvent event) throws IOException, SQLException{
        loadingChanges();
        root=FXMLLoader.load(getClass().getResource("../graphicStyle/userPage.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void sugestPage(MouseEvent event)  throws IOException, SQLException{
        loadingChanges();
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
        } catch (SQLException | IOException ex) {
            Logger.getLogger(IngredientPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    void start() throws SQLException, IOException {
        for(String i : categories){
            IngredientsToggleButtons o = new IngredientsToggleButtons(i);
            ToggleButtonsLists.add(o);
            for(ToggleButtonObject j : o.getButtons()){
                @SuppressWarnings("UnusedAssignment")
                ToggleButton toggleButton= new ToggleButton();
                  toggleButton=j.getToggleButton();
                  switch (i) {
                    case "Fruit" -> fruitPane.getChildren().add(toggleButton);
                    case "Vegetable" -> vegetablePane.getChildren().add(toggleButton);
                    case "Dairy" -> dairyPane.getChildren().add(toggleButton);
                    case "Nuts & Cereal" -> nutsPane.getChildren().add(toggleButton);
                    case "Fungus & Legume" -> fungusPane.getChildren().add(toggleButton);
                    case "Plant" -> plantPane.getChildren().add(toggleButton);
                    case "Meat" -> meatPane.getChildren().add(toggleButton);
                    case "Fish" -> fishPane.getChildren().add(toggleButton);
                    case "Seafood" -> seafoodPane.getChildren().add(toggleButton);
                    case "Herbs & Spices" -> spicePane.getChildren().add(toggleButton);
                    case "Additive & Oil" -> additivePane.getChildren().add(toggleButton);
                    case "Beverage" -> beveragePane.getChildren().add(toggleButton);
                    case "Other" -> otherPane.getChildren().add(toggleButton);
                    default -> {
                    }
                }
             }
         }
        
    }
private void loadingChanges() throws SQLException {
    try (Connection connection = DataBaseManager.getConnection()) {
        String queryDelete = "DELETE FROM UserIngredient WHERE id_user = ? AND id_ingredient = ?";
        String queryInsert = "INSERT INTO UserIngredient (id_user, id_ingredient) VALUES (?, ?)";
        
        List<Integer> userIngredientIds = DataBaseController.getUserIngredientIds();

        for (IngredientsToggleButtons i : ToggleButtonsLists) {
            for (ToggleButtonObject j : i.getButtons()) {
                int id = Integer.parseInt(j.toggleButton.getId());
                boolean isSelected = j.toggleButton.isSelected();
                boolean isExistingIngredient = userIngredientIds.contains(id);

                if (!isExistingIngredient && isSelected) {
                    try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
                        statementInsert.setInt(1, User.id);
                        statementInsert.setInt(2, id);
                        statementInsert.executeUpdate();
                    }
                } else if (isExistingIngredient && !isSelected) {
                    try (PreparedStatement statementDelete = connection.prepareStatement(queryDelete)) {
                        statementDelete.setInt(1, User.id);
                        statementDelete.setInt(2, id);
                        statementDelete.executeUpdate();
                    }
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}

}
