
package frontendControllers;

import backend.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpPageController  implements Initializable  {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField emailFeild;

    @FXML
    private TextField passwordFeild;

    @FXML
    private Text signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameFeild;

    @FXML
    void loginPage(MouseEvent event) throws IOException {
            root=FXMLLoader.load(getClass().getResource("../graphicStyle/loginPage.fxml"));
            stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    void signUpFunction(ActionEvent event) throws IOException {
       String username = usernameFeild.getText();
        String password = passwordFeild.getText();
        String email = emailFeild.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        } else {
            int validate = User.signUp(username, password, email);
            if (validate == 1) {
                root = FXMLLoader.load(getClass().getResource("../graphicStyle/recipesPage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (validate == -1) {
                    alert.setContentText("Email or Username already asocieted to an acount");
                } else {
                    alert.setContentText("Error at sign up");
                }
                alert.showAndWait();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    private void start() {
         signUpButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
        signUpButton.setOnMouseEntered(event -> {
                signUpButton.setStyle("-fx-background-color: #5B3A8A; -fx-text-fill: white;");
            });

         signUpButton.setOnMouseExited(event -> {
                signUpButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
            });
         
    }


}
