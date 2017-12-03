
package db.server.integration;

import db.common.FileInfo;
import db.server.controller.Controller;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataHandler {
    private static final String TABLE_NAME = "user";
    private PreparedStatement statement;
    Connection connection;
    Controller myController;
    public DataHandler(){
        
        String url = "jdbc:mysql://localhost:3306/filehosting";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            try{
                connection = DriverManager.getConnection(url, "root","");
                System.out.println("connected to db");
                
                
            }catch(SQLException e){
                System.out.println("not connected to db");
            }
          
    }
    
    public void addController(Controller controller){
        this.myController = controller;
    }
    
    public void addUser(String username, String password){
        try {
            statement = connection.prepareStatement("INSERT INTO user VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("added to db");
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
        public boolean deleteUser(String username, String password){
        
            boolean success = false;
            //checks that the user pass combination is correct
            if(login(username, password)){
                try {
                statement = connection.prepareStatement("DELETE FROM user WHERE name = ?");
                statement.setString(1, username);
                statement.executeUpdate(); 

                System.out.println("deleted user from db " +username);
                success = true;
                } catch (SQLException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
             return success;   
    }
        
        private void reportChanges(FileInfo fileInfo, String user){
            String username;
            try {
            statement = connection.prepareStatement("SELECT name FROM user WHERE name = ?");
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

				username = rs.getString("name");
                                myController.report(fileInfo, username);
                                

			}
            
            } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
      
            
        }
    

    public void addFileInfo(FileInfo fileInfo){

           try {
            statement = connection.prepareStatement("INSERT INTO file VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, fileInfo.filename);
            statement.setInt(2, fileInfo.size);
            statement.setString(3, fileInfo.owner);
            statement.setBoolean(4, fileInfo.publ);
            statement.setBoolean(5, fileInfo.writable);
            statement.setString(6, fileInfo.log);
            statement.executeUpdate();
            System.out.println("added file to db " +fileInfo.filename);
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void updateFileInfo(FileInfo fileInfo, String user){

    
        try {
            statement = connection.prepareStatement("UPDATE file SET size = ?, public = ?, writable = ?, log = ? WHERE name = ?");

            
            statement.setInt(1, fileInfo.size);
            statement.setBoolean(2, fileInfo.publ);
            statement.setBoolean(3, fileInfo.writable);
            statement.setString(4, fileInfo.log);
            statement.setString(5, fileInfo.filename);
            statement.executeUpdate();
            
            reportChanges(fileInfo, user);
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    } 
    
    public void deleteFileInfo(FileInfo fileInfo){
        
    
        try {
            statement = connection.prepareStatement("DELETE FROM file WHERE name = ?");
            statement.setString(1,fileInfo.filename);
            statement.executeUpdate(); 

        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    public boolean login(String user, String pass){
        boolean correct = false;
        
            try {
            statement = connection.prepareStatement("SELECT name, password FROM user WHERE name = ?");
            statement.setString(1, user);
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

				if(rs.getString("name").equals(user) && rs.getString("pass").equals(pass)){
                                    correct = true;
                                    System.out.println("correct user and password");
                                }else{
                                   System.out.println("wrong user and/or password"); 
                                }
                                    
			}
            
            } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
         
        return correct;
    }
    
    public boolean register(String user, String pass){
        boolean available = false;
        
            try {
            statement = connection.prepareStatement("SELECT name FROM user WHERE name = ?");
            statement.setString(1, user);
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

				if(rs.getString("name").equals(user)){
                                    
                                    
                                }else{
                                    
                                    available = true;
                                    addUser(user, pass);
                                     
                                }
                                    
			}
            
            } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
         
        return available;
    }
    
    
}
