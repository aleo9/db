
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
    
        public FileInfo[] lookupFiles() throws NotBoundException, MalformedURLException, RemoteException{
        
        lookupServer(host);
        //String s = server.getFiles(myRemoteObj, username);
        FileInfo[] files = server.getFiles(myRemoteObj, username);
        return files;
      
    }
    
    public void login(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
        
        lookupServer(host);
        loggedIn = server.login(myRemoteObj, username, password);
        
        if(loggedIn){
            System.out.println("logged in " +loggedIn);
            this.username = username;
        }
      
    }
    
        public void logout(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
        
        lookupServer(host);
        loggedIn = server.logout(myRemoteObj, username, password);
        System.out.println("logged in " +loggedIn);
     
    }
        
        
    
    public void register(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
            
        lookupServer(host);
        loggedIn = server.register(myRemoteObj, username, password);
        
        if(loggedIn){
            System.out.println("logged in" +loggedIn);
            this.username = username;
        }else{
            System.out.println("couldn't register");
        }
      
    }
    
        public void unregister(String username, String password) throws NotBoundException, MalformedURLException, RemoteException{
            
        lookupServer(host);
        boolean success = server.unregister(myRemoteObj, username, password);
        if(success){
            System.out.println("removed user " +username);
        }else{
            System.out.println("couldn't remove user. Wrong username or password");
        }
      
    }
    
    
    
    public void edit(String filename, boolean publ, boolean writable) throws RemoteException{
        
        if(loggedIn){
            server.edit(filename, publ, writable, username);
            
        
            System.out.println("changed " +filename);
        }else{
            System.out.println("not logged in");
        }
        
        
    }

    
    public void upload(String filename, boolean publ, boolean writable) throws RemoteException{
        
        if(loggedIn){
            FileInfo fileInfo = new FileInfo(filename, 225, username, publ, writable, "");
            //int serverPort = server.upload(myRemoteObj, username, filename, fileInfo);
            int serverPort = server.upload(myRemoteObj, username, filename, fileInfo);
            fileClient.bind(serverPort);
        
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
    
    public void download(String filename) throws RemoteException{
        if(loggedIn){
            int serverPort = server.download(myRemoteObj, username, filename);
            fileClient.bind(serverPort);
            try {
                fileClient.saveFile(filename);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("not logged in");
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