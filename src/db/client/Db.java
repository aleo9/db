
package db.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class Db extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest((ae) -> {
        Platform.exit();
        System.exit(0);
        });
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
        @Override
    public void stop(){
    }

    public static void main(String[] args) {
        launch(args);
        
    }
    
}
