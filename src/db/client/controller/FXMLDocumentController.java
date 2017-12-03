
package db.client.controller;

import db.common.FileInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class FXMLDocumentController implements Initializable {
    

    @FXML
    private TextField userfield;
    @FXML
    private TextField passfield;
    @FXML
    private TextField filename;
    @FXML
    private TextField editFile;
    
    @FXML
    private TextArea textArea;
    
    @FXML
    private CheckBox publ;
    @FXML
    private CheckBox writing;
    
    @FXML
    private Button loginButton;
    //private String host;
    
    private static Controller controller;
    
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.login(userfield.getText(), passfield.getText());
        //loginButton.setText("Logout");
        
        
    }
    
    @FXML
    private void logoutButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.logout(userfield.getText(), passfield.getText());
        
    }
    
    @FXML
    private void registerButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.register(userfield.getText(), passfield.getText());
        
    }
    @FXML
    private void editButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.edit(editFile.getText(), publ.isSelected(), writing.isSelected());
        
    }

    
    @FXML
    private void unregisterButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.unregister(userfield.getText(), passfield.getText());
        
    }
    
    @FXML
    private void filesButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        //textArea.setText("s");
        FileInfo[] files = controller.lookupFiles();
        //String files = controller.lookupFiles();
        //System.out.println(files[0].filename);
        //label.setText("s");
        //textArea = new TextArea("s");
        
        if(files.length!=0){
                StringBuilder text = new StringBuilder(""); 
                //String text;
                for (int i = 0; i<files.length; i++){
                    
                //text = (String) files[i].filename;
                //System.out.println(files[i].filename);
                //text +="\n";
                text.append(files[i].filename+" ");
                text.append(+files[i].size+"bytes ");
                text.append("created by "+files[i].owner+" ");
                if(files[i].publ){
                    text.append("public ");
                }else{
                    text.append("private ");
                }
                if(files[i].writable){
                text.append("writable" +"\n");
                }else{
                    text.append("not writable" +"\n");
                }
                
                
            //System.out.println(text);
                                textArea.setText(text.toString());
                //textArea.append(files[i].filename);
                }
        //textArea.setText("s");
                //textArea.setText(text);
            }
        
        
        
        
        //printToTextArea(files);
        
    }

    
    @FXML
    private void uploadButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.upload(filename.getText(), publ.isSelected(), writing.isSelected());
        
        
        //label.setText("Login!");
    }
        @FXML
    private void downloadButtonAction(ActionEvent event) throws NotBoundException, MalformedURLException, RemoteException{
        
        
        controller.download(filename.getText());
        
    }
  
   
    
        private void printToTextArea(FileInfo[] files){
            
            if(files.length!=0){
                //StringBuilder text = new StringBuilder(""); 
                String text;
                for (int i = 0; i<=files.length; i++){
                text = (String) files[i].filename;
                //System.out.println(files[i].filename);
                //text +="\n";
                //text.append("    Field "+files[i].filename+"\n");
            System.out.println(text);
                
                //textArea.append(files[i].filename);
                }
        //textArea.setText("s");
                //textArea.setText(text);
            }
        
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        try{
        controller = new Controller();
        
        }catch(RemoteException e){
        }    
        
        
    }    
    
}






