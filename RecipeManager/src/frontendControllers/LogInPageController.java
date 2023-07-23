
package frontendControllers;


import backend.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

public class LogInPageController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button loginButton;
    
    @FXML
    private TextField emailUsernameFeild;

    @FXML
    private TextField passwordFeild;
    
    @FXML
    private Text signUpButton;
    
    private ActionEvent event;

    @FXML
    void LogInFunction(ActionEvent event) throws IOException, SQLException {
        int validate=User.validateLogin(emailUsernameFeild.getText(), passwordFeild.getText());
        if(validate==1){
            if("admin".equals(emailUsernameFeild.getText())){
                root=FXMLLoader.load(getClass().getResource("../graphicStyle/recipesAdminPage.fxml"));
                stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                root=FXMLLoader.load(getClass().getResource("../graphicStyle/recipesPage.fxml"));
                stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            if(validate==0){
                alert.setContentText("Invalid password");
            }
            else{
                alert.setContentText("Invalid username or email");
            }
            alert.showAndWait();
        } 
}
        @FXML
        void signUpPage(MouseEvent event) throws IOException {
            root=FXMLLoader.load(getClass().getResource("../graphicStyle/signUpPage.fxml"));
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
         loginButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
        loginButton.setOnMouseEntered(event -> {
                loginButton.setStyle("-fx-background-color: #5B3A8A; -fx-text-fill: white;");
            });

         loginButton.setOnMouseExited(event -> {
                loginButton.setStyle("-fx-background-color: #764bb2; -fx-text-fill: white;");
            });
         
    }
        
}

