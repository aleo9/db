
package db.client.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField userfield;
    @FXML
    private TextField passfield;
    //private String host;
    
    private static Controller controller;
    
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        System.out.println("Trying to Login");
        
        controller.login(userfield.getText(), passfield.getText());
        
        
        label.setText("Login!");
    }
    
    @FXML
    private void uploadButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        System.out.println("Trying to Upload");
        
        //controller.upload("x.txt");
        
        
        //label.setText("Login!");
    }
    
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("x2");
        label.setText("herro");
        try{
            System.out.println("controller made");
        controller = new Controller();
        controller.hello();
        }catch(RemoteException e){
            System.out.println("controller not made");
        }    
        
        
    }    
    
}






