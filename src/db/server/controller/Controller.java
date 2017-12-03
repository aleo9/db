
package db.server.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import db.common.ServerInterface;
import db.common.ClientInterface;
import db.common.FileInfo;
import db.server.integration.DataHandler;
import db.server.model.UserManager;
import db.server.net.FileServer;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

                                //exporting
public class Controller extends UnicastRemoteObject implements ServerInterface {
    private final UserManager userManager = new UserManager();
    private FileServer fileServer;
    private int serverPort = 1010;
    DataHandler dataHandler;
    
    public Controller() throws RemoteException {
        System.out.println("controller created");
        fileServer = new FileServer(serverPort);
        System.out.println("fileserver created");
        dataHandler = new DataHandler();
        System.out.println("datahandler created");

    }
    
    public void report(FileInfo fileInfo, String username){
        //report to logged in user
    }
    
    @Override
    public int upload(ClientInterface remoteNode, String username, String filename, FileInfo fileInfo) throws RemoteException{
        System.out.println("user tries to upload file");
        CompletableFuture.runAsync(() -> {
            try {
                fileServer.establishConnection();
                fileServer.saveFile(filename);
                dataHandler.addFileInfo(fileInfo);
                dataHandler.addController(this);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end CompletableFuture
        
        
        
        return serverPort;
    }
    
        @Override
    public int download(ClientInterface remoteNode, String username, String filename) throws RemoteException{
        System.out.println("user tries to download file");
        CompletableFuture.runAsync(() -> {
            try {
                fileServer.establishConnection();
                fileServer.sendFile(filename);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end CompletableFuture
        
        
        
        return serverPort;
    }
    

    @Override
    public boolean register(ClientInterface remoteNode, String username, String password) {
        System.out.println("user tries to register");
        
        //1. check db
        boolean success = dataHandler.register(username, password);
        //2. add user to usermanager
        if(success){
            userManager.loginUser(remoteNode, username);
        }
        
        
        //userManager.sendConvToParticipant(participantId);
        return success;
    }
    
    @Override
    public boolean unregister(ClientInterface remoteNode, String username, String password) {
        System.out.println("user tries to unregister");
        
        //1. delete from db
        boolean success = dataHandler.deleteUser(username, password);
        
        //2. logout
        if(success){
            userManager.logoutUser(username);
        }
        
        return success;
    }
    
    @Override
    public FileInfo[] getFiles(String user) {
        System.out.println("user tries to obtain file info");
        FileInfo[] files = userManager.getFiles(user);
        //userManager.sendConvToParticipant(participantId);
        return files;
    }
    
    @Override
    public boolean login(ClientInterface remoteNode, String username, String password) {
        System.out.println("user tries to login");
        
        //1. check db
        boolean success = dataHandler.login(username, password);
        //2. add user to usermanager
        if(success){
            userManager.loginUser(remoteNode, username);
        }
        
        
        //userManager.sendConvToParticipant(participantId);
        return success;
    }





}
