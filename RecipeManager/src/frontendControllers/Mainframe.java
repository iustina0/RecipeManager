
package frontendControllers;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 *
 * @author Iustina
 */
public class Mainframe extends Application  {
    private static Stage primaryStage;
    public static  void main(String[] args) {
        launchApp(args);
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root=FXMLLoader.load(getClass().getResource("../graphicStyle/loginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
         stage.show();
    }
   
    }
    
