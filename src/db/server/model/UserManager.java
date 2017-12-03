
package db.server.model;

import db.common.ClientInterface;
import db.common.FileInfo;
import java.util.LinkedList;
import java.util.List;


public class UserManager {
    
    List<User> loggedIn = new LinkedList<>(); 
    
        public void logoutUser(String username){
            for(User user : loggedIn){
            
                if(user.getName().equals(username)){
                //remove any logins from user.
                loggedIn.remove(user);
                }
            }
        }
    
        public void loginUser(ClientInterface remoteNode, String username) {
            //remove any previous logins from same user.
            logoutUser(username);
        
            User user = new User(username, remoteNode);
            loggedIn.add(user);

    }
    
    public FileInfo[] getFiles(String user) {
        
        FileInfo[] files;
        if(loggedIn.contains(user)){
            //do query returning filtered by username&private + public files
            //number 5 replaced with total of query
            int totalFetched = 5;
            files = new FileInfo[totalFetched];
            
            for(int i = 0; i<totalFetched; i++){
                //paste all info from database into file object
            }
            
        }else{
            files = new FileInfo[0];
        }
        return files;
    
    }
    
    //get the client of the username, if you want to send some info to a specific user
    public ClientInterface getClient(String username) {
        for(User user : loggedIn){
            if(user.getName().equals(username)){
                return user.getClient();
            }
        }

        
        return null;
    }


}