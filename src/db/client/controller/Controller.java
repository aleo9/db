
package db.client.controller;

import db.client.net.FileClient;
import db.common.ClientInterface;
import db.common.FileInfo;
import db.common.ServerInterface;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {
    public ServerInterface server;
    String host;
    private final ClientInterface myRemoteObj;
    private FileClient fileClient;
    private String username;
    private boolean loggedIn;

    public Controller() throws RemoteException{
        myRemoteObj = new OutputClient();
        host = "localhost";
        fileClient = new FileClient();
    }
    
    public void login(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
        
        lookupServer(host);
        loggedIn = server.login(myRemoteObj, username, password);
        System.out.println("logged in" +loggedIn);
        if(loggedIn){
            this.username = username;
        }
            

    }
    
    public void register(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
            
        lookupServer(host);
        loggedIn = server.register(myRemoteObj, username, password);
        System.out.println("logged in" +loggedIn);
        if(loggedIn){
            this.username = username;
        }
      
    }
    
        public void unregister(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
            
        lookupServer(host);
        boolean success = server.unregister(myRemoteObj, username, password);
        if(success){
            System.out.println("removed user " +username);
        }
        
        
      
    }
    
    public void hello(){
        System.out.println("hi");
    }
                
    public void upload(String filename, int size, String owner, boolean publ, boolean writable, String log) throws RemoteException{
        
        if(loggedIn){
            FileInfo fileInfo = new FileInfo(filename, size, owner, publ, writable, log);
            int serverPort = server.upload(myRemoteObj, username, filename, fileInfo);
            fileClient.bind(serverPort);
            System.out.println("upload 2");
        
            try {
            fileClient.sendFile(filename);
            
            } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            System.out.println("uploaded " +filename);
        }else{
            System.out.println("not logged in");
        }
        
        
    }
    
    public void download(String username, String filename) throws RemoteException{
        server.download(myRemoteObj, username, filename);
        try {
            fileClient.saveFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //lookup, establish access to controller methods in server
    public void lookupServer(String host) throws NotBoundException, MalformedURLException,
                                                  RemoteException {
        server = (ServerInterface) Naming.lookup(
                "//" + host + "/" + ServerInterface.SERVER_NAME_IN_REGISTRY);
    }
    
    private class OutputClient extends UnicastRemoteObject implements ClientInterface {

        public OutputClient() throws RemoteException {
        }

            @Override
            public void receiveMessage(String message) {
            System.out.println(message);
            }
    }
    
}